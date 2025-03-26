package travel_book.service.web.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;
import travel_book.service.domain.member.Member;
import travel_book.service.web.profile.dto.ProfileData;
import travel_book.service.web.session.SessionConst;




@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

   private final ProfileService profileService;

    @GetMapping("/{userId}")
    public String profileView(@PathVariable(value = "userId") String userId, Model model
            , @SessionAttribute(name = SessionConst.SESSION_NAME, required = false) Member member) {
        log.info("세션정보={}",member);

        ProfileData profileData = profileService.getProfile(userId);

        model.addAttribute("profileData", profileData);
        return "profile";
        //return "/travel/travel-list";
    }
    @GetMapping("/favicon.ico")
    public ResponseEntity<Void> favicon() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content 응답
    }
}
