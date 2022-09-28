package com.catchbug.server.board;

import com.catchbug.server.board.dto.*;
import com.catchbug.server.board.exception.AlreadyHiredException;
import com.catchbug.server.board.exception.ExpiredBoardException;
import com.catchbug.server.board.exception.NotCreateException;
import com.catchbug.server.board.exception.NotFoundBoardException;
import com.catchbug.server.employ.Employ;
import com.catchbug.server.employ.EmployService;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.catchbug.server.employ.EmployEntityTest.setUpEmploy;
import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@MockBean(JpaMetamodelMappingContext.class)
@SpringBootTest(classes = BoardService.class)
public class BoardServiceTest {

    @MockBean
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private EmployService employService;

    @MockBean
    private Member memberEntity;

    @MockBean
    private Board mockBoardEntity;

    @MockBean
    private Employ mockEmployEntity;

    @DisplayName("방이 정상적으로 생성되어야 한다.")
    @Test
    public void create_room_OnSuccess() throws Exception{
        //given
        Member member = setUpMember();
        DtoOfCreateBoard dtoOfCreateBoard = DtoOfCreateBoard.builder()
                .title("테스트제목")
                .content("테스트내용")
                .build();
        Board board = Board.builder()
                .id(1L).build();
        given(boardRepository.save(any())).willReturn(board);
        given(memberService.getMember(member.getId())).willReturn(member);
        given(memberEntity.getLatestBoard()).willReturn(null);
        //when
        DtoOfCreatedBoard createdBoard = boardService.createBoard(member.getId(), dtoOfCreateBoard);
        //then
        Assertions.assertEquals(1L, createdBoard.getRoomId());
    }

    @DisplayName("사용자의 최근 게시판이 10분이 지났으면 true 가 리턴되어야 한다.")
    @Test
    public void check_Creatable_OnFalse() throws Exception{
        //given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime mockTime = LocalDateTime.now().minusMinutes(11L);
        //when
        given(memberEntity.getLatestBoard()).willReturn(mockTime);
        boolean actual = boardService.checkCreatable(memberEntity);
        //then
        Assertions.assertTrue(actual);
    }

    @DisplayName("사용자의 최근 게시판이 10분이 지났지 않았으면 false 가 리턴되어야 한다.")
    @Test
    public void check_Creatable_OnTrue() throws Exception{
        //given
        LocalDateTime mockTime = LocalDateTime.now().minusMinutes(3L);
        //when
        given(memberEntity.getLatestBoard()).willReturn(mockTime);
        boolean actual = boardService.checkCreatable(memberEntity);
        //then

        Assertions.assertFalse(actual);

    }

    @DisplayName("사용자가 글을 쓴 적이 없으면 true 가 리턴되어야 한다.")
    @Test
    public void check_Creatable_OnNull() throws Exception{
        //given & when & mocking
        given(memberEntity.getLatestBoard()).willReturn(null);
        boolean actual = boardService.checkCreatable(memberEntity);

        //then
        Assertions.assertTrue(actual);

    }

    @DisplayName("이전에 생성한 방이 활성화 상태이면, NotCreateException 예외가 생긴다.")
    @Test
    public void check_NotCreateException() throws Exception{

        //given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime mockTime = LocalDateTime.now().minusMinutes(9L);
        DtoOfCreateBoard dtoOfCreateBoard = DtoOfCreateBoard.builder()
                .title("테스트제목")
                .content("테스트내용")
                .build();
        //when
        given(memberService.getMember(1L)).willReturn(memberEntity);
        given(memberEntity.getLatestBoard()).willReturn(mockTime);

        //then
        Assertions.assertThrows(NotCreateException.class,() -> {
            boardService.createBoard(1L, dtoOfCreateBoard);
        });
    }

    @DisplayName("글이 정상적으로 조회되어야 한다.")
    @Test
    public void getBoardTest() throws Exception{
        //given
        Board boardEntity = setUpBoard();
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(boardEntity));
        DtoOfGetBoard expectedBoard = DtoOfGetBoard.builder()
                .creatorId(boardEntity.getHost().getId())
                .creatorNickname(boardEntity.getHost().getNickname())
                .city(boardEntity.getCity())
                .region(boardEntity.getRegion())
                .town(boardEntity.getTown())
                .detailLocation(boardEntity.getDetailLocation())
                .longitude(boardEntity.getLongitude())
                .latitude(boardEntity.getLatitude())
                .roomContent(boardEntity.getContent())
                .roomTitle(boardEntity.getTitle())
                .build();
        //when
        DtoOfGetBoard actualBoard = boardService.getBoard(1L);
        //then
        Assertions.assertEquals(expectedBoard.getCity(), actualBoard.getCity());
        Assertions.assertEquals(expectedBoard.getRegion(), actualBoard.getRegion());
        Assertions.assertEquals(expectedBoard.getDetailLocation(), actualBoard.getDetailLocation());
        Assertions.assertEquals(expectedBoard.getTown(), actualBoard.getTown());
        Assertions.assertEquals(expectedBoard.getLatitude(), actualBoard.getLatitude());
        Assertions.assertEquals(expectedBoard.getLongitude(), actualBoard.getLongitude());
        Assertions.assertEquals(expectedBoard.getCreatorNickname(), actualBoard.getCreatorNickname());
        Assertions.assertEquals(expectedBoard.getRoomContent(), actualBoard.getRoomContent());
        Assertions.assertEquals(expectedBoard.getRoomTitle(), actualBoard.getRoomTitle());
        Assertions.assertEquals(expectedBoard.getCreatorId(), actualBoard.getCreatorId());
    }

    @DisplayName("글이 존재하지 않는 경우에는 예외가 발생해야한다.")
    @Test
    public void not_found_board_ExceptionTest(){
        //given
        //mocking
        given(boardRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        //when
        //then
        Assertions.assertThrows(NotFoundBoardException.class, () -> {
            boardService.getBoard(1L);
        });
    }

    @DisplayName("모든 지역의 board 개수를 정상적으로 출력한다.")
    @Test
    public void get_boards_count(){

        //given & mocking
        List<DtoOfGetRegionCount> dtoOfGetRegionCountList = new ArrayList<>();
        given(boardRepository.getRegionCount()).willReturn(dtoOfGetRegionCountList);
        //when
        List<DtoOfGetRegionCount> actualResult = boardService.getRegionCount();

        //then
        Assertions.assertNotNull(actualResult);

    }

    @DisplayName("해당 city 의 개수를 정상적으로 출력한다.")
    @Test
    public void get_cities_boards_count() throws Exception{

        //given & mocking
        List<DtoOfGetCityCount> dtoOfGetCityCountList = new ArrayList<>();
        given(boardRepository.getCityCount(anyString())).willReturn(dtoOfGetCityCountList);
        //when
        List<DtoOfGetCityCount> actualResult = boardService.getCityCount("동작구");

        //then
        Assertions.assertNotNull(actualResult);


    }

    @DisplayName("해당 town 의 개수를 정상적으로 출력한다.")
    @Test
    public void get_town_boards_count() throws Exception{

        //given & mocking
        List<DtoOfGetTownCount> dtoOfGetTownCountList = new ArrayList<>();
        given(boardRepository.getTownCount(anyString())).willReturn(dtoOfGetTownCountList);
        //when
        List<DtoOfGetTownCount> actualResult = boardService.getTownCount("상원동");

        //then
        Assertions.assertNotNull(actualResult);

    }


    @DisplayName("해당 town 의 게시 글을 정상적으로 페이징 처리하여 조회한다.")
    @Test
    public void get_town_boards_OnSuccess() throws Exception{

        //given & mocking
        Member member = setUpMember();
        Board board = Board.builder()
                .id(1L)
                .host(member)
                .latitude(123123.123123)
                .longitude(321321.321321)
                .town("상원동")
                .city("강남구")
                .content("아무나")
                .title("아무나")
                .region("서울시")
                .detailLocation("상원빌라 1층")
                .build();
        List<Board> mockBoardList = new ArrayList<>();
        mockBoardList.add(board);
        Page<Board> townBoardPage = new PageImpl<Board>(mockBoardList, PageRequest.of(10, 10), 10);
        DtoOfGetTownBoards.builder()
                .page(townBoardPage.getPageable().getPageSize())
                .totalPages(townBoardPage.getTotalPages())
                .size(townBoardPage.getSize())
                .dtoOfBoardList(townBoardPage.getContent()
                        .stream()
                        .map(v -> DtoOfBoard
                                .builder()
                                .nickName(v.getHost().getNickname())
                                .content(v.getContent())
                                .title(v.getTitle())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        given(boardRepository.findAllByTown(anyString(), any())).willReturn(townBoardPage);
        //when
        DtoOfGetTownBoards actualResult = boardService.getTownBoards("상원동", PageRequest.of(10, 10));
        DtoOfBoard actualBoards = actualResult.getDtoOfBoardList().get(0);
        //then
        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(board.getTitle(), actualBoards.getTitle());
        Assertions.assertEquals(board.getContent(), actualBoards.getContent());
        Assertions.assertEquals(board.getHost().getNickname(), actualBoards.getNickName());
        Assertions.assertEquals(board.getId(), actualBoards.getId());
        Assertions.assertEquals(10, actualResult.getSize());
        Assertions.assertEquals(10, actualResult.getPage());
    }

    @DisplayName("글에 배치되지않았을 경우 정상적으로 통과한다.")
    @Test
    public void checkAlreadyHired_OnEmployNull() throws Exception{

        //given
        Board board = setUpBoard();

        //when
        //then
        Assertions.assertDoesNotThrow(() -> {
            board.checkAlreadyHired();
        });

    }

    @DisplayName("글에 배치되어있을 경우 AlreadyHiredException 발생한다.")
    @Test
    public void check_AlreadyHiredException() throws Exception{

        //given
        Board board = Board.builder()
                .region("region")
                .town("town")
                .city("city")
                .detailLocation("detailLocation")
                .longitude(123.123123)
                .latitude(321.321321)
                .host(setUpMember())
                .content("content")
                .title("title")
                .id(1L)
                .employ(mockEmployEntity)
                .build();;

        //when
        //then
        Assertions.assertThrows(AlreadyHiredException.class, () -> {
            board.checkAlreadyHired();
        });

    }

    @DisplayName("매칭된 글을 정상적으로 조회한다.")
    @Test
    public void getBoardTest_OnMatchedBoard() throws Exception{

        //given & mocking
        Member member = setUpMember();
        Board board = setUpMatchedBoard(mockEmployEntity);

        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));
        given(mockEmployEntity.getId()).willReturn(1L);
        given(mockEmployEntity.getEmployee()).willReturn(member);
        given(mockEmployEntity.getEmployer()).willReturn(member);
        DtoOfGetBoard expectedResult = DtoOfGetBoard
                .builder()
                .expiryTime(LocalDateTime.now().plusMinutes(10))
                .creatorId(member.getId())
                .id(1L)
                .creatorNickname(member.getNickname())
                .latitude(board.getLatitude())
                .longitude(board.getLongitude())
                .roomTitle(board.getTitle())
                .roomContent(board.getContent())
                .town(board.getTown())
                .city(board.getCity())
                .region(board.getRegion())
                .detailLocation(board.getDetailLocation())
                .build();
        //when

        DtoOfGetBoard actualResult = boardService.getBoard(1L);
        //then
        Assertions.assertEquals(expectedResult.getCreatorId(), actualResult.getCreatorId());
        Assertions.assertEquals(expectedResult.getRoomContent(), actualResult.getRoomContent());
        Assertions.assertEquals(expectedResult.getRoomTitle(), actualResult.getRoomTitle());
        Assertions.assertEquals(expectedResult.getLongitude(), actualResult.getLongitude());
        Assertions.assertEquals(expectedResult.getLatitude(), actualResult.getLatitude());
        Assertions.assertEquals(expectedResult.getTown(), actualResult.getTown());
        Assertions.assertEquals(expectedResult.getRegion(), actualResult.getRegion());
        Assertions.assertEquals(expectedResult.getCity(), actualResult.getCity());
        Assertions.assertEquals(expectedResult.getDetailLocation(), actualResult.getDetailLocation());
        Assertions.assertEquals(expectedResult.getId(), actualResult.getId());
        Assertions.assertNotNull(actualResult.getEmploy());

    }
    @DisplayName("매칭되지 않은 글을 정상적으로 조회한다.")
    @Test
    public void getBoardTest_OnWaitingBoard() throws Exception{

        //given & mocking
        Member member = setUpMember();
        Board board = setUpBoard();

        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));
        DtoOfGetBoard expectedResult = DtoOfGetBoard
                .builder()
                .expiryTime(LocalDateTime.now().plusMinutes(10))
                .creatorId(member.getId())
                .id(1L)
                .creatorNickname(member.getNickname())
                .latitude(board.getLatitude())
                .longitude(board.getLongitude())
                .roomTitle(board.getTitle())
                .roomContent(board.getContent())
                .town(board.getTown())
                .city(board.getCity())
                .region(board.getRegion())
                .detailLocation(board.getDetailLocation())
                .build();
        //when

        DtoOfGetBoard actualResult = boardService.getBoard(1L);
        //then
        Assertions.assertEquals(expectedResult.getCreatorId(), actualResult.getCreatorId());
        Assertions.assertEquals(expectedResult.getRoomContent(), actualResult.getRoomContent());
        Assertions.assertEquals(expectedResult.getRoomTitle(), actualResult.getRoomTitle());
        Assertions.assertEquals(expectedResult.getLongitude(), actualResult.getLongitude());
        Assertions.assertEquals(expectedResult.getLatitude(), actualResult.getLatitude());
        Assertions.assertEquals(expectedResult.getTown(), actualResult.getTown());
        Assertions.assertEquals(expectedResult.getRegion(), actualResult.getRegion());
        Assertions.assertEquals(expectedResult.getCity(), actualResult.getCity());
        Assertions.assertEquals(expectedResult.getDetailLocation(), actualResult.getDetailLocation());
        Assertions.assertEquals(expectedResult.getId(), actualResult.getId());
        Assertions.assertNull(actualResult.getEmploy());

    }

    public static Board setUpBoard(){
        return Board.builder()
                .region("region")
                .town("town")
                .city("city")
                .detailLocation("detailLocation")
                .longitude(123.123123)
                .latitude(321.321321)
                .host(setUpMember())
                .content("content")
                .title("title")
                .id(1L)
                .status(Status.WAITING)
                .build();
    }

    public static Board setUpMatchedBoard(Employ employ){
        return Board.builder()
                .region("region")
                .town("town")
                .city("city")
                .detailLocation("detailLocation")
                .longitude(123.123123)
                .latitude(321.321321)
                .host(setUpMember())
                .content("content")
                .title("title")
                .id(1L)
                .status(Status.MATCHED)
                .employ(employ)
                .build();
    }




}
