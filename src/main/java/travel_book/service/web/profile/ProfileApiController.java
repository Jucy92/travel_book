package travel_book.service.web.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel_book.service.domain.member.Member;
import travel_book.service.web.session.SessionConst;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProfileApiController {

    private final ProfileService profileService;
    @PostMapping("/posts")
    public ResponseEntity<?> loadPosts(@RequestBody Map<String, Object> requestData) {
        String userId = (String) requestData.get("userId");
        List<?> travelList = profileService.getPosts(userId);   // 아직 개발 안함 여행정보 그대로 호출

        return ResponseEntity.ok().body(travelList);
    }
    @PostMapping("/travel")
    public ResponseEntity<?> loadTravel(@RequestBody Map<String, Object> requestData) {
        String userId = (String) requestData.get("userId");
        List<?> travelList = profileService.getTravelList(userId);

        return ResponseEntity.ok().body(travelList);
    }
    @GetMapping("/travel/{travelId}")
    public ResponseEntity<?> loadTravelDetail(@PathVariable(name = "travelId") Long travelId,
                                              @SessionAttribute(name = SessionConst.SESSION_NAME, required = false) Member sessionMember) {
        log.info("travelId={}",travelId);
        log.info("Member={}", sessionMember);
        List<?> travelInformation = profileService.getTravelInformation(travelId);
        log.info("travelInformationList={}",travelInformation);

        //Map<String, Object> responseMap = new HashMap<>();
        //responseMap.put("travelInformation", travelInformation);

        return ResponseEntity.ok().body(travelInformation); // 이렇게 바로 넘기면 화면단에서 data[n]로 바로 호출해서 List 안에 데이터 접근
        //return ResponseEntity.ok().body(responseMap);     // 맵으로 담아서 넘기면 data.travelInformation[n] 으로 호출해야함

    }

}
