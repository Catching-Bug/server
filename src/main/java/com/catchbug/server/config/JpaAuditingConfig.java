package com.catchbug.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * <h1>JpaAuditingConfig</h1>
 * <p>
 *     <h3>Configuration for JPA Auditing</h3>
 * </p>
 * <p>
 *     <h3>JPA Auditing 을 위한 클래스</h3>
 *     테스트에서 Bean 주입을 위해서 따로 클래스를 생성한다.
 * </p>
 *
 * @see com.catchbug.server.board.Board
 * @author younghoCha
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
