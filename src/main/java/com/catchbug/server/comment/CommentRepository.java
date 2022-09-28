package com.catchbug.server.comment;

import com.catchbug.server.board.Board;
import com.catchbug.server.comment.dto.DtoOfGetComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    Page<DtoOfGetComment> findByBoardId(Long boardId, Pageable pageable);

}
