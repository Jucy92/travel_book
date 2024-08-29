package travel_book.service.web.home;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.web.session.SessionConst;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;

    /*
    public HomeController(MemberRepository memberRepository) {  // @RequiredArgsConstructor에서 빈 컨테이너에 등록되어 있는 memberRepository로 등록
        this.memberRepository = memberRepository;
    }
    */
    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.SESSION_NAME, required = false) Member loginMember, Model model){

        if (loginMember == null) {
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    /*
        @GetMapping("/map")
        public String map() {
            return "/map/travelMap";
        }
        */
    /*
    @GetMapping("/trip-planner")
    public String calll() {

    }
    */

}
