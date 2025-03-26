package travel_book.service.web.profile.service;

import jakarta.mail.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.web.map.dto.TravelList;
import travel_book.service.web.map.service.MapServiceMybatis;
import travel_book.service.web.profile.ProfileService;
import travel_book.service.web.profile.dto.ProfileData;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {
    private final MemberRepository memberRepository;
    private final MapServiceMybatis mapServiceMybatis;
    @Override
    public ProfileData getProfile(String userId) {
        // 처음에 불러오는거부터
        log.info("컨트롤러에서 받은 userId={}",userId);
        ProfileData profileData = memberRepository.findProfileDataByUserId(userId);
        profileData.setProfileImage("/images/1742192223179_KakaoTalk_20250310_205957096.jpg");  // 나중에 DB에 경로, 파일명 넣고 쿼리에서 처리하게 변경

        log.info("profileData={}",profileData);
        return profileData;
    }

    @Override
    public List<TravelList> getPosts(String userId) {
        List<TravelList> posts = mapServiceMybatis.findByTravel(memberRepository.findIdByUserId(userId));
        log.info("posts={}",posts);

        return posts;
    }

    @Override
    public List<?> getTravelList(String userId) {
        List<TravelList> travelList = mapServiceMybatis.findByTravel(memberRepository.findIdByUserId(userId));
        log.info("travelList={}",travelList);

        return travelList;
    }

    @Override
    public List<?> getTravelInformation(Long travelId) {
        return mapServiceMybatis.findTravelInfoByTravelId(travelId);

    }


}
