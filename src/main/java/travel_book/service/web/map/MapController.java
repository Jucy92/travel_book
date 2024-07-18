package travel_book.service.web.map;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.web.map.dto.LocationModel;
import travel_book.service.web.map.service.MapService;
import travel_book.service.web.session.SessionConst;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Controller
public class MapController {

    private final MapService mapService;
    private final MemberRepository memberRepository;
    public MapController(MapService mapService, MemberRepository memberRepository) {
        this.mapService = mapService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/map")
    public String showMap() {
//        return "/map/gptMap";
        return "/map/kakaoMap";
    }

    @PostMapping("/map")
    public ResponseEntity<String> saveLocations(@RequestBody List<LocationModel> locations
            , @SessionAttribute(name = SessionConst.SESSION_NAME, required = false) Member member) {

//        HttpSession session = request.getSession();       // @SessionAttribute으로 바로 Member에서 가져오기
//        session.getAttribute(SessionConst.SESSION_NAME);

        locations.forEach(location -> {
            location.setMemberId(member.getId());
//            location.setMemberId(1L);           // 로그인 안하고 테스트 할 때 사용 + WebConfig LoginCheckInterceptor 주석
            log.info("location={}", location);
            mapService.saveLocation(location);    // 반복 돌면서 데이터 저장
        });
        return new ResponseEntity<>("위치들이 성공적으로 저장되었습니다!", HttpStatus.CREATED);
    }

    @GetMapping("/map/retrieve/{userId}")
    @ResponseBody   // http 강의 다시 들어야 할듯..  + GetMapping return 타입 페이지 아니고 값 넘기려면 무조건 @ResponseBody 가 있었어야 했나..?
    public List<LocationModel> retrieveLocation(@PathVariable(value = "userId") String userId,
                                                @SessionAttribute(name = SessionConst.SESSION_NAME, required = false) Member loginMember) {

        if (userId.isEmpty()) {        // 아이디 입력안하고 바로 가져오기 했을 때 세션에서 로그인 정보자 데이터 가져오기
            //Optional<Member> member = memberRepository.memberInfoFindByUser(loginMember.getUserId()); // 아래처럼 orElseThrow 처리하면 됨
            Member member = memberRepository.memberInfoFindByUser(loginMember.getUserId()).orElseThrow(); // orElseThrow 있으면 벨류값(객체) 없으면 노서치인셉션
            log.info("저장소(사용자명x) -> Con -> memberInfoFindByUser -> Member ={}",member);
            return mapService.LocationFindById(member.getId());

        } else {
            Member member = memberRepository.memberInfoFindByUser(userId).orElseThrow();    // orElseThrow 있으면 벨류값(객체) 없으면 노서치인셉션
            log.info("저장소(사용자명o) -> Con -> memberInfoFindByUser -> Member ={}",member);
            return mapService.LocationFindById(member.getId());
        }
    }
}
