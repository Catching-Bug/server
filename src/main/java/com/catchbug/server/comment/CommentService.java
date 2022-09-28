package com.catchbug.server.comment;

import com.catchbug.server.board.Board;
import com.catchbug.server.board.BoardService;
import com.catchbug.server.comment.dto.DtoOfCreateComment;
import com.catchbug.server.comment.dto.DtoOfCreatedComment;
import com.catchbug.server.comment.dto.DtoOfGetComment;
import com.catchbug.server.comment.dto.DtoOfGetComments;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * <h1>CommentService</h1>
 * <p>
 *     Business Object of Comment
 * </p>
 * <p>
 *     댓글에 대한 비즈니스 로직 클래스
 * </p>
 *
 * @see com.catchbug.server.comment.Comment
 * @see com.catchbug.server.comment.CommentController
 * @see com.catchbug.server.comment.CommentRepository
 * @author younghoCha
 */
@RequiredArgsConstructor
@Service
public class CommentService {
    private final BoardService boardService;
    private final MemberService memberService;
    private final CommentRepository commentRepository;

    /**
     * 댓글 작성 메서드
     * @param memberId : 댓글을 다는 사람의 id(pk)
     * @param boardId : 댓글 작성될 게시글 id(pk)
     * @param dtoOfCreateComment : 댓글 작성을 위한 dto
     * @return 댓글 작성에 대한 서버 응답 dto
     */
    public DtoOfCreatedComment createComment(Long memberId, Long boardId, DtoOfCreateComment dtoOfCreateComment){
        Member memberEntity = memberService.getMember(memberId);
        Board boardEntity = boardService.getBoardEntity(boardId);
        System.out.println("content = " + dtoOfCreateComment.getContent());
        Comment commentEntity = Comment.builder()
                .commenter(memberEntity)
                .board(boardEntity)
                .content(dtoOfCreateComment.getContent())
                .build();

        Comment savedCommentEntity = commentRepository.save(commentEntity);

        return DtoOfCreatedComment.builder()
                .commentId(savedCommentEntity.getId())
                .commenterNickname(memberEntity.getNickname())
                .content(savedCommentEntity.getContent())
                .commentedAt(savedCommentEntity.getCommentedAt())
                .build();

    }

    public Page<DtoOfGetComment> getComments(Long boardId, Pageable pageable){

        Page<DtoOfGetComment> comments = commentRepository.findByBoardId(boardId, pageable);

        return comments;

    }
}
