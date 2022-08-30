package com.catchbug.server.comment;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <h1>CommentRepository</h1>
 * <p>
 *     DAo Of Comment
 * </p>
 * <p>
 *     댓글 DAO
 * </p>
 *
 * @see com.catchbug.server.comment.Comment
 * @see com.catchbug.server.comment.CommentService
 * @author younghoCha
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
