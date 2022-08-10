package com.catchbug.server.board;

import com.catchbug.server.board.dto.DtoOfCreateBoard;
import com.catchbug.server.board.dto.DtoOfCreatedBoard;
import com.catchbug.server.board.dto.DtoOfGetBoard;
import com.catchbug.server.board.exception.NotCreateException;
import com.catchbug.server.board.exception.NotFoundBoardException;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
    private Member memberEntity;


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
    public void not_found_board_ExceptionTest() throws Exception{


        //given
        Board boardEntity = setUpBoard();
        //mocking
        given(boardRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        //when
        //then
        Assertions.assertThrows(NotFoundBoardException.class, () -> {
            boardService.getBoard(1L);
        });


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
                .build();
    }

}
