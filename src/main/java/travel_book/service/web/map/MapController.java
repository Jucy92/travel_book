package travel_book.service.web.map;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.web.map.dto.LocationModel;
import travel_book.service.web.map.dto.RouteResponse;
import travel_book.service.web.map.service.MapService;
import travel_book.service.web.session.SessionConst;

import java.util.List;
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
//        return "/map/kakaoMap";
        return "/map/googleMap";
    }

    @PostMapping("/map")
    public ResponseEntity<String> saveLocations(@RequestBody List<LocationModel> locations
            , @SessionAttribute(name = SessionConst.SESSION_NAME, required = false) Member member) {

        //HttpSession session = request.getSession();       // @SessionAttribute으로 바로 Member에서 가져오기
        //session.getAttribute(SessionConst.SESSION_NAME);

        // 메인 테이블(LOCATION_STORAGE_M) 데이터 추가 / + 지금은 postman해서 userId 넘겨줬지만, 화면에서 등록하려면 세션 member id 값 가져오거나, 화면단에서 구해서 locations에 담아서 보내줘야함
        mapService.saveStorageM(locations.stream().findFirst().orElse(null));       // saveStorageM을 리턴 받으면 쿼리 결과 값인 1(insert 1건 성공)을 리턴 받는다. => 하면서 자동으로 keyProperty 설정된 값에 생성된 값을 매핑해서 객체를 반환해 준다.
        Long travelId = locations.stream().findFirst().orElse(null).getTravelId();  // 첫번째 리스트 객체로 insert 되었기 때문에 거기에만 travelId 값이 들어가있다

        locations.forEach(location -> {
            //location.setUserId(member.getUserId());   // 화면단에서 userId 미리 넣어주는거 아니면 여기도 넣어줘야함
            location.setTravelId(travelId);             // 마스터 테이블에 저장한 여행번호 가져와서 넣어주기
            mapService.saveStorageD(location);          // 반복 돌면서 데이터 저장
        });
        return new ResponseEntity<>("데이터가 정상적으로 저장되었습니다!", HttpStatus.CREATED);
        //return ResponseEntity.ok("경로가 저장되었습니다");
    }

    @GetMapping("/map/retrieve/{userId}")
    @ResponseBody   // http 강의 다시 들어야 할듯..  + GetMapping return 타입 페이지 아니고 값 넘기려면 무조건 @ResponseBody 가 있었어야 했나..?
    public List<LocationModel> retrieveLocation(@PathVariable(value = "userId") String userId,
                                                @SessionAttribute(name = SessionConst.SESSION_NAME, required = false) Member loginMember) {

        if (userId.isEmpty()) {        // 아이디 입력안하고 바로 가져오기 했을 때 세션에서 로그인 정보자 데이터 가져오기
//            Optional<Member> member = memberRepository.memberInfoFindByUser(loginMember.getUserId()); // 아래처럼 orElseThrow 처리하면 됨
            Member member = memberRepository.memberInfoFindByUser(loginMember.getUserId()).orElseThrow(); // orElseThrow 있으면 벨류값(객체) 없으면 노서치인셉션
            log.info("저장소(사용자명x) -> Con -> memberInfoFindByUser -> Member ={}",member);
            return mapService.LocationFindById(member.getId());

        } else {
            Member member = memberRepository.memberInfoFindByUser(userId).orElseThrow();    // orElseThrow 있으면 벨류값(객체) 없으면 노서치인셉션
            log.info("저장소(사용자명o) -> Con -> memberInfoFindByUser -> Member ={}",member);
            return mapService.LocationFindById(member.getId());
        }
    }
    @GetMapping("/storage/{userId}")
    public String StorageUser(@PathVariable(value = "userId") String userId, Model model) {     // 모델에 담아서 보내기 model -> responseBody??
        //log.info("userId={}",userId);     웹에서 인코딩 하는 문제로 storageMFindByUserId 들어갈 때 꺠져서 들어가네... -> 타임리프 문제로 ${userId} 에서 + userId로 변경함
        //log.info("List={}",mapService.storageMFindByUserId(userId));
        model.addAttribute("list", mapService.storageMFindByUserId(userId));
        return "/map/locationStorage";

    }
    @PostMapping("/storage/{travelId}")
    @ResponseBody
    public List<LocationModel> locationStorage(@PathVariable(value = "travelId") String travelId) {

        log.info("travelId={}",travelId);


        return mapService.storageDFindByTravelId(Long.parseLong(travelId));     
        // 리스트로 해서 다 가져옴 => postHandle에서 안찍히는 이유는 리스폰스쪽에 모델에 안담겼고, 다른 화면(뷰)도 호출 안하기 떄문
        //    ㄴ> 내용은 웹브라우저 개발자도구에서 확인 가능
    }

    /**
     * ID 검색 - 검색된 계정 페이지 - 마음에 드는 여행 리스트 선택 - 가져가기(친구이거나, 어떤 조건에서만 가져갈 수 있도록 불펌금지)
     *기능 = ㄴ>검색   ㄴ>저장된 리스트 조회                       ㄴ> 복사
     */

    /*      // 카카오맵 도보 경로 받아오기 위해 선언했던 내용
    @GetMapping("/map/route")
    public RouteResponse getRoute(@RequestParam double startLat, @RequestParam double startLng,
                                  @RequestParam double endLat, @RequestParam double endLng) {
        log.info("startLat={}, startLng={}, endLat={}, endLng={}", startLat, startLng, endLat, endLng);
        return mapService.getWalkingRoute(startLat, startLng, endLat, endLng);

    }
    */
}
