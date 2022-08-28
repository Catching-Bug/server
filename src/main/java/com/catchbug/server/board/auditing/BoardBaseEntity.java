package com.catchbug.server.board.auditing;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;


/**
 * <h1>BoardBaseEntity</h1>
 * <p>
 *     Board Entity Auditing Class
 * </p>
 * <p>
 *     Board Entity 의 Auditing 기능 클래스
 * </p>
 *
 * @see com.catchbug.server.board.Board
 * @author younghoCha
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BoardBaseEntity {

    /**
     * 엔티티가 생성된 시간
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;

}
