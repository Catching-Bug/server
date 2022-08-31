package com.catchbug.server.comment;

import com.catchbug.server.comment.dto.DtoOfCreateComment;
import com.catchbug.server.comment.dto.DtoOfCreatedComment;
import com.catchbug.server.comment.dto.DtoOfGetComment;
import com.catchbug.server.comment.dto.DtoOfGetComments;
import com.catchbug.server.common.response.Response;
import com.catchbug.server.jwt.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * <h1>CommentController</h1>
 * <p>
 *     Comment Controller from mvc pattern
 * </p>
 * <p>
 *     mvc 패턴의 컨트롤러
 * </p>
 *
 * @see com.catchbug.server.comment.Comment
 * @see com.catchbug.server.comment.CommentService
 * @author younghoCha
 */
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 생성 메서드
     * @param authUser : 요청자 Authentication 객체
     * @param boardId : 댓글 작성 게시 글
     * @param dtoOfCreateComment : 작성하려는 댓글에 대한 요청 dto
     * @return : 서버 응답 데이터
     */
    @PostMapping("/api/comment/{boardId}")
    public ResponseEntity createComment(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long boardId, @RequestBody DtoOfCreateComment dtoOfCreateComment){

        DtoOfCreatedComment dtoOfCreatedComment = commentService.
                createComment(Long.valueOf(authUser.getId()), boardId, dtoOfCreateComment);

        Response response = Response.builder()
                .message("댓글이 정상적으로 등록되었습니다.")
                .content(dtoOfCreatedComment)
                .build();

        return new ResponseEntity(response, HttpStatus.OK);

    }

    @GetMapping("/api/comments/{boardId}")
    public ResponseEntity getComments(@PathVariable Long boardId, Pageable pageable){
        Page<DtoOfGetComment> comments = commentService.getComments(boardId, pageable);
        Response response = Response.builder()
                .message("정상적으로 댓글을 조회했습니다.")
                .content(comments)
                .build();

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
