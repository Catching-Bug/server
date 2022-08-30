package com.catchbug.server.comment.auditing;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * <h1>CommentBaseEntity</h1>
 * <p>
 *     Comment Entity Auditing Class
 * </p>
 * <p>
 *     Comment Entity 의 Auditing 기능 클래스
 * </p>
 *
 * @see com.catchbug.server.comment.Comment
 * @author younghoCha
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class CommentBaseEntity {

    /**
     * 엔티티가 생성된 시간
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime commentedAt;
}
