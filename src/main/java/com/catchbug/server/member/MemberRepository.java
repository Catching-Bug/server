package com.catchbug.server.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <h1>MemberRepository</h1>
 * <p>
 *     DAO of Member Entity
 * </p>
 * <p>
 *     회원 정보 DAO
 * </p>
 *
 * @see com.catchbug.server.member.MemberService
 * @author younghoCha
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    /**
     * 최초 로그인인지 확인하는 메서드
     * @param kakaoId : Oauth2에서 제공받은 서비스 id
     * @return : 존재시 true, 비존재 시 false
     */
    boolean existsByKakaoId(long kakaoId);

    /**
     * Oauth2에서 제공받은 id를 통해서 사용자 엔티티를 찾는 메서드
     * @param kakaoId : Oauth2에서 제공받은 id
     * @return Optional<Member> : id를 통해서 찾은 엔티티(영속화)
     */
    Optional<Member> findByKakaoId(long kakaoId);
}
