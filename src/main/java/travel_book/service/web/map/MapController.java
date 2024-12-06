package travel_book.service.web.map;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import travel_book.service.domain.map.Travel;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.web.map.dto.LocationModel;
import travel_book.service.web.map.dto.TravelData;
import travel_book.service.web.map.service.MapServiceJpa;
import travel_book.service.web.map.service.MapServiceMybatis;
import travel_book.service.web.session.SessionConst;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MapController {

    private final MapServiceMybatis mapServiceMybatis;
    private final MapServiceJpa mapServiceJpa;
    private final MemberRepository memberRepository;

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
        mapServiceMybatis.saveStorageM(locations.stream().findFirst().orElse(null));       // saveStorageM을 리턴 받으면 쿼리 결과 값인 1(insert 1건 성공)을 리턴 받는다. => 하면서 자동으로 keyProperty 설정된 값에 생성된 값을 매핑해서 객체를 반환해 준다.
        Long travelId = locations.stream().findFirst().orElse(null).getTravelId();  // 첫번째 리스트 객체로 insert 되었기 때문에 거기에만 travelId 값이 들어가있다

        locations.forEach(location -> {
            //location.setUserId(member.getUserId());   // 화면단에서 userId 미리 넣어주는거 아니면 여기도 넣어줘야함
            location.setTravelId(travelId);             // 마스터 테이블에 저장한 여행번호 가져와서 넣어주기
            mapServiceMybatis.saveStorageD(location);          // 반복 돌면서 데이터 저장
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
            log.info("저장소(사용자명x) -> Con -> memberInfoFindByUser -> Member ={}", member);
            return mapServiceMybatis.LocationFindById(member.getId());

        } else {
            Member member = memberRepository.memberInfoFindByUser(userId).orElseThrow();    // orElseThrow 있으면 벨류값(객체) 없으면 노서치인셉션
            log.info("저장소(사용자명o) -> Con -> memberInfoFindByUser -> Member ={}", member);
            return mapServiceMybatis.LocationFindById(member.getId());
        }
    }

    @GetMapping("/storage/{userId}")
    public String StorageUser(@PathVariable(value = "userId") String userId, Model model) {     // 모델에 담아서 보내기 model -> responseBody??
        //log.info("userId={}",userId);     웹에서 인코딩 하는 문제로 storageMFindByUserId 들어갈 때 꺠져서 들어가네... -> 타임리프 문제로 ${userId} 에서 + userId로 변경함
        //log.info("List={}",mapService.storageMFindByUserId(userId));
        model.addAttribute("list", mapServiceMybatis.storageMFindByUserId(userId));
        return "/map/locationStorage";

    }

    @PostMapping("/storage/{travelId}")
    @ResponseBody
    public List<LocationModel> locationStorage(@PathVariable(value = "travelId") String travelId) {

        log.info("travelId={}", travelId);


        return mapServiceMybatis.storageDFindByTravelId(Long.parseLong(travelId));  // 리턴 타입이 List<LocationModel>
        // 리스트로 해서 다 가져옴 => postHandle에서 안찍히는 이유는 리스폰스쪽에 모델에 안담겼고, 다른 화면(뷰)도 호출 안하기 떄문
        //    ㄴ> 내용은 웹브라우저 개발자도구에서 확인 가능
    }

    /**
     * 여행지 저장
     */
    @GetMapping("/travel/add")
    public String travelAddPage() {
        return "/travel/travel-add";
    }

    @PostMapping("/travel/add")
    @ResponseBody
    public ResponseEntity<String> addItinerary(@RequestBody Map<String, Object> data, @SessionAttribute(value = SessionConst.SESSION_NAME, required = false) Member member) {
        // 데이터를 각 테이블로 쪼개서 받냐, 아니면 TravelData에 한번에 담아서 여러번 받냐......... 화면 만들 때 다시 고민
        if (member == null) {
            Member tempMember = memberRepository.findByMember("test9").orElse(null);
            member = tempMember;
        }

/*+
//        String travelId = (String) data.get("travelId");

        List<Map<String, Object>> locations = (List<Map<String, Object>>) data.get("locations");
        List<Map<String, Object>> locationDetails = (List<Map<String, Object>>) data.get("locationDetails");
        log.info("RequestBody={}", data);
        Object title = data.get("title");
        log.info("title", title);
        //log.info("locations", locations);
        //log.info("locationDetails", locationDetails);
        for (Map<String, Object> location : locations) {
            log.info("locationId={}, lat={}, lng={}", location.get("locationId"), location.get("lat"), location.get("lng"));
        }
        for (Map<String, Object> locationDetail : locationDetails) {
            log.info("locationId={}, locationSq={}, content={}", locationDetail.get("locationId"), locationDetail.get("locationSq"), locationDetail.get("content"));
        }
        */

        mapServiceJpa.addItinerary(data, member.getId());
        return new ResponseEntity<>("데이터가 정상적으로 저장되었습니다!", HttpStatus.CREATED);
    }

    /**
     * ID 검색 - 검색된 계정 페이지 - 마음에 드는 여행 리스트 선택 - 가져가기(친구이거나, 어떤 조건에서만 가져갈 수 있도록 불펌금지)
     * 기능 = ㄴ>검색   ㄴ>저장된 리스트 조회                       ㄴ> 복사            ㄴ> 사용자가 허락/요청/거절 권한 넣어서 허락이면 불펌 가능, 요청은 승인 있어야 가능
     */

    @GetMapping("/travel")
    public String travelWithUser() {    // @ModelAttribute("loginModel")LoginModel loginModel   -> 타임리프 object 기능 사용하려면 쓰면 됨
        // 단순 아이디 입력하는 화면 뿌려주는 용도
        return "home";
    }

    /*
    //타임리프나, jsp 로 처리하는 방법
    @PostMapping("/travel/{userId}")
    public String travelList(@RequestParam(value = "userId") String userId, Model model) {   // 화면이 있다면, 화면에 입력 받은 영역 이름을 userId로 사용해야함

        List<TravelData> travels = mapService.findByTravel(memberRepository.findById(userId));  // 사용자 아이디에 등록되어 있는 여행 정보 전부 출력

        model.addAttribute("travel_list", travels);   // 화면에서 값 받아서 쓰려면 타임리프 th:object 기능 써야함,
        // 그냥 jsp 하려면 따로 담아야하나 싶지만, jsp도 리스트 넘겨받아서 처리하는 방법이 있겠지~

        return "화면명";
    }
    */
    // Json 방식으로 데이터 받고 보내서 처리
    @PostMapping(value = "/travel/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<TravelData> travelInfo(@PathVariable(value = "userId") String userId) {   // 사용자 아이디에 등록되어 있는 여행 정보 전부 출력

        List<TravelData> travelsList = mapServiceMybatis.findByTravel(memberRepository.findById(userId));

        // model.attribute("화면명", travels);   // 근데 화면에서 값 받아서 쓰려면 타임리프 th:object 기능 쓰거나, -> 이렇게 하려면 @ResponseBody 제거
        // 아래 방식으로 보내면 화면에서 받아서 자바스크립트로 처리
        return travelsList; // @ResponseBody 추가하고 이렇게 보내면 자동으로 JSON 타입으로 전달 -> 자바스크립트에서 받아서 보여주고 선택해서 여행번호까지 보내주면 그 건에 대해서 처리
    }

    @PostMapping("/travel/{userId}/{travelId}")
    @ResponseBody
    public List<TravelData> travelList(@PathVariable(value = "userId") String userId,       // (사용자 아이디,) 여행 번호 넘겨 받아 해당 여행 정보 출력 -> 여행 번호가 중복이 안되니 삭제해도 무방할듯..?
                                       @PathVariable(value = "travelId") long travelId) {   //  @RequestParam으로 받으면 문자열로만 받았던거 같은데 여기는 다른 타입 가능

        long id = memberRepository.findById(userId);        // 사용자 ID 가져오기
        List<TravelData> travelList = mapServiceMybatis.findByTravelId(id, travelId);
        log.info("travelId={}", travelList);

        // model.attribute("화면명", travels);   // 근데 화면에서 값 받아서 쓰려면 타임리프 th:object 기능 쓰거나, -> 이렇게 하려면 @ResponseBody 제거
        // 아래 방식으로 보내면 화면에서 받아서 자바스크립트로 처리
        return travelList; // @ResponseBody 추가하고 이렇게 보내면 자동으로 JSON 타입으로 전달 -> 자바스크립트에서 받아서 보여주고 선택해서 여행번호까지 보내주면 그 건에 대해서 처리
    }

    @PostMapping("/travel/copy/{travelId}")
    @ResponseBody
    public ResponseEntity<Travel> copyOfAllItinerary(@PathVariable("travelId") long travelId,
                                                     @SessionAttribute(name = SessionConst.SESSION_NAME, required = false) Member member) {
        if (member == null) {
            Member tempMember = memberRepository.findByMember("juchje1").orElse(null);
            member = tempMember;
        }

        Travel copiedTravel = mapServiceJpa.copyOfAllItinerary(travelId, member.getId());
        return ResponseEntity.ok(copiedTravel);
    }

}
