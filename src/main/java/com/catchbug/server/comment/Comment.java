package com.catchbug.server.comment;

import com.catchbug.server.board.Board;
import com.catchbug.server.comment.auditing.CommentBaseEntity;
import com.catchbug.server.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * <h1>Comment</h1>
 * <p>
 *     Comment Entity Class
 * </p>
 * <p>
 *     댓글 엔티티
 * </p>
 *
 * @see CommentService
 * @author younghoCha
 */
@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends CommentBaseEntity {
    /**
     * 댓글 id(pk)
     */
    @Id
    @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 댓글 작성자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENTER_ID")
    private Member commenter;

    /**
     * 댓글이 달린 게시글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    /**
     * 댓글 내용
     */
    @Column(name = "CONTENT")
    private String content;


}
