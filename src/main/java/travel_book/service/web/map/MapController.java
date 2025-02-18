package travel_book.service.web.map;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.web.map.dto.TravelDetail;
import travel_book.service.web.map.dto.TravelList;
import travel_book.service.web.map.service.MapServiceJpa;
import travel_book.service.web.map.service.MapServiceMybatis;
import travel_book.service.web.session.SessionConst;

import java.util.*;


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

    /**
     * 여행지 저장
     */
    @GetMapping("/travel/add")
    public String travelAddPage() {
        return "/travel/travel-add";
    }

    @PostMapping("/travel/add")
    public ResponseEntity<String> addItinerary(@RequestBody Map<String, Object> data, @SessionAttribute(value = SessionConst.SESSION_NAME, required = false) Member member) {
        // 데이터를 각 테이블로 쪼개서 받냐, 아니면 TravelData에 한번에 담아서 여러번 받냐......... 화면 만들 때 다시 고민
        if (member == null) {
            Member tempMember = memberRepository.findByMember("test9").orElse(null);
            member = tempMember;
        }

        mapServiceMybatis.addItinerary(data, member.getId());
        //mapServiceJpa.addItinerary(data, member.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("데이터가 정상적으로 저장되었습니다!");

    }

    /**
     * ID 검색 - 검색된 계정 페이지 - 마음에 드는 여행 리스트 선택 - 가져가기(친구이거나, 어떤 조건에서만 가져갈 수 있도록 불펌금지)
     * 기능 = ㄴ>검색   ㄴ>저장된 리스트 조회                       ㄴ> 복사            ㄴ> 사용자가 허락/요청/거절 권한 넣어서 허락이면 불펌 가능, 요청은 승인 있어야 가능
     */


    @GetMapping("/travel/{userId}")
    public String travelList(@PathVariable(value = "userId") String userId, Model model/*, ModelAndView mv*/) {
        List<TravelList> list = mapServiceMybatis.findByTravel(memberRepository.findById(userId));
        model.addAttribute("travelList", list);  // 이렇게 한다고 데이터를 가지고 list 화면으로 넘어가나?
        //mv.getModel().put("travelList", travelList);  // -> 필요 없음 model + return String 으로 전달됨
        //mv.setViewName("/travel/travel-list");
        return "/travel/travel-list";
    }


    @PostMapping("/travel/{travelId}")
    // travel/파라미터 하나 날아오는걸로 userId인지 travelId인지 구별 못함, 순서 바꿔도 안돼서 아래 주석 sonsumers = JSON이라 우선으로 잡혔나..?
    @ResponseBody
    public ResponseEntity<List<TravelDetail>> travelList(@PathVariable(value = "travelId") long travelId) {   //  @RequestParam으로 받으면 문자열로만 받았던거 같은데 여기는 다른 타입 가능
        List<TravelDetail> detail = mapServiceMybatis.findByTravelId(travelId);
        log.info("travelId={}", detail);

        return ResponseEntity.status(HttpStatus.OK).body(detail);
    }

    /*  
    // Json 방식으로 데이터 받고 보내는 테스트 용도 / 실제 동작은 위 화면에서 진행
    @PostMapping(value = "/travel/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)   // 화면 만들기 위해 JSON 타입 해지
    @ResponseBody
    public List<TravelData> travelInfo(@PathVariable(value = "userId") String userId) {   // 사용자 아이디에 등록되어 있는 여행 정보 전부 출력

        List<TravelData> travelsList = mapServiceMybatis.findByTravel(memberRepository.findById(userId));

        // model.attribute("화면명", travels);   // 근데 화면에서 값 받아서 쓰려면 타임리프 th:object 기능 쓰거나, -> 이렇게 하려면 @ResponseBody 제거
        // 아래 방식으로 보내면 화면에서 받아서 자바스크립트로 처리
        return travelsList; // @ResponseBody 추가하고 이렇게 보내면 자동으로 JSON 타입으로 전달 -> 자바스크립트에서 받아서 보여주고 선택해서 여행번호까지 보내주면 그 건에 대해서 처리
    }
     */

    @PostMapping("/travel/copy/{travelId}")
    //public ResponseEntity<Travel> copyOfAllItinerary(@PathVariable("travelId") long travelId,
    public ResponseEntity<Map<String,String>> copyOfAllItinerary(@PathVariable("travelId") long travelId,
                                                          @RequestBody Map<String, String> requestMessage,
                                                          @SessionAttribute(name = SessionConst.SESSION_NAME, required = false) Member member) {
        if (member == null) {
            Member tempMember = memberRepository.findByMember("juchje1").orElse(null);
            member = tempMember;
        }

        log.info("requestMassage={}", requestMessage);

        mapServiceMybatis.copyOfAllItinerary(travelId, member.getId(), requestMessage.get("title"));     // 저장된 결과 값 받고 싶으면 서비스 로직에서 TravelData에 저장된 리턴 값 set으로 담던가~
        //Travel copiedTravel = mapServiceJpa.copyOfAllItinerary(travelId, member.getId());
        Map<String, String> response = new HashMap<>();
        response.put("message", "정상적으로 복사 했습니다.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
