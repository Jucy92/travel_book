package travel_book.service.web.member;

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
    public String save(@Validated @ModelAttribute Member member, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("hasErrors = {}", member);   // 에러 발생 했을 때.. login쪽에서는 에러 뱉었던거 같은데 여기는 반응이 없네... 뭔 차이더라..
            return "members/addMemberForm";
        }
        log.info("전달 받은 Member = {}", member);
        memberRepository.save(member);
        return "redirect:/";
//        return "redirect:/login/loginForm"; // -> 이렇게 하면 해당 페이지에서 받는 object 명과 달라서 오류 발생 -> object 명을 바꾸면.. loginModel이 아닌 Member 검증 처리
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
