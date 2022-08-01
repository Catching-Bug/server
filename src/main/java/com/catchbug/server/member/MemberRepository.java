package com.catchbug.server.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByKakaoId(long kakaoId);

    Optional<Member> findByKakaoId(long kakaoId);
}
