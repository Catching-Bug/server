package com.catchbug.server.member;

import com.catchbug.server.location.Location;
import com.catchbug.server.member.dto.DtoOfGetLocation;
import com.catchbug.server.member.dto.DtoOfGetMember;
import com.catchbug.server.member.exception.NotFoundMemberException;
import com.catchbug.server.oauth2.dto.DtoOfUserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>MemberService</h1>
 * <p>
 *     Service Of Member Entity And Business Logic Object
 * </p>
 * <p>
 *     Member에 대한 Business Logic을 가지고 있는 클래스
 * </p>
 *
 * @see com.catchbug.server.member.Member
 * @see com.catchbug.server.member.MemberService
 * @see com.catchbug.server.member.MemberRepository
 * @author younghoCha
 */
@RequiredArgsConstructor
@Service
public class MemberService {
    /**
     * Member Entity DAo
     */
    private final MemberRepository memberRepository;

    /**
     * 최초 로그인 판단 메서드
     * @param kakaoId : Oauth2에서 제공받은 유저 id
     * @return 최초 로그인 시, true 응답 / 최초 로그인이 아닐 시, false 응답
     */
    public boolean isFirstLogin(Long kakaoId){

        //최초 로그인 시, true
        boolean isFirst = memberRepository.existsByKakaoId(kakaoId);

        //최초 로그인 시, false 리턴
        return !isFirst;
    }

    /**
     * 사용자 login 메서드
     * @param userProfile : Oauth2 서버로부터 받은 사용자 정보
     * @return Member : DB에 저장된 멤버(영속화 상태)
     */
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

    /**
     * 사용자의 데이터를 조회하기 위한 메서드
     * @param id : Member id(pk)
     * @return Member : 영속화 상태인 Member Entity
     */
    public Member getMember(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("해당하는 회원을 찾을 수 없습니다."));

        return member;
    }

    /**
     * 사용자가 지정한 위치 정보 리스트를 리턴하는 메소드
     * @param id : 요청자 id
     * @return : 요청자가 사전에 등록한 위치정보 리스트 dto, 사전에 등록한 위치정보가 없으면 null
     */
    public List<DtoOfGetLocation> getMemberLocation(Long id){
        Member memberEntity = getMember(id);

        if(memberEntity.getLocations() == null){
            return null;
        }

        return memberEntity.getLocations().stream().map(location ->
                DtoOfGetLocation.builder()
                        .locationName(location.getLocationName())
                        .detailLocation(location.getDetailLocation())
                        .city(location.getCity())
                        .town(location.getTown())
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude())
                        .region(location.getRegion())
                        .build())
                .collect(Collectors.toList());

    }

    /**
     * 멤버의 정보를 조회하는 메소드
     * @param id : 조회하려는 멤버 id
     * @return : 멤버에 대한 정보 dto
     */
    public DtoOfGetMember getMemberInformation(Long id){

        Member memberEntity = getMember(id);

        return DtoOfGetMember.builder()
                .nickname(memberEntity.getNickname())
                .Gender(memberEntity.getGender())
                .build();

    }



}
