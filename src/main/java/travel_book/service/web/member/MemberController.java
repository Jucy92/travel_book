package travel_book.service.web.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.web.session.SessionConst;

import java.time.LocalDateTime;
//import travel_book.service.domain.member.MemberRepository;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute Member member, BindingResult bindingResult, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            log.info("hasErrors = {}", member);   // 에러 발생 했을 때.. login쪽에서는 에러 뱉었던거 같은데 여기는 반응이 없네... 뭔 차이더라..
            return "members/addMemberForm";
        }
        log.info("전달 받은 Member = {}", member);
        memberRepository.save(member);
        
        // 회원 가입 하면 바로 로그인+홈화면 이동을 위해 -> 세션 생성
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.SESSION_NAME, member);
        log.info("session={}", session.getAttribute(SessionConst.SESSION_NAME));
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editForm(Member member) { //(@ModelAttribute("member"))
        return "members/editMemberForm";
    }

    @PostMapping("/edit")
    public String updateForm(@ModelAttribute Member member, BindingResult bindingResult,
                             @SessionAttribute(name = SessionConst.SESSION_NAME, required = false) Member loginMember) {
        if (bindingResult.hasErrors()) {
            log.info("hasErrors = {}",member);
            return "members/editMemberForm";
        }
        log.info("updateMember={}", member);
        member.setId(loginMember.getId());
        memberRepository.update(member);
//        session.setAttribute(SessionConst.SESSION_NAME, loginMember);     // 내가 여기서 세션 정보를 다시 덮어도 상관없나... 이거 설정하면 홈화면에서 변경된 사용자 정보 보일텐데
        
        return "redirect:/";
    }

}
