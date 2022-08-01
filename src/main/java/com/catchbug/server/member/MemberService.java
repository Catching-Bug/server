package com.catchbug.server.member;

import com.catchbug.server.oauth2.dto.DtoOfUserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean isFirstLogin(Long kakaoId){

        //최초 로그인 시, true
        boolean isFirst = memberRepository.existsByKakaoId(kakaoId);

        //최초 로그인 시, false 리턴
        return !isFirst;
    }

    public Member login(DtoOfUserProfile userProfile){
        boolean isFirst = isFirstLogin(userProfile.getId());

        if(isFirst){
            Member newMember = Member.builder()
                    .gender(Gender.MALE)
                            .nickname(userProfile.getProperties().getNickname())
                                    .kakaoId(userProfile.getId())
                                            .build();
            Member savedMember = memberRepository.save(newMember);

            return savedMember;
        }

        Member memberEntity = memberRepository.findByKakaoId(userProfile.getId()).get();

        return memberEntity;

    }
}
