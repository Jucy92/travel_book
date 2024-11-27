package travel_book.service.web.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import travel_book.service.domain.login.serivce.LoginService;
import travel_book.service.domain.member.Member;
import travel_book.service.web.login.model.LoginModel;
import travel_book.service.web.session.SessionConst;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginModel")LoginModel loginModel) { // /login 화면 호출 될 때 던져주는 값이 없는데 왜 이렇게 썼던거야..? -> 이게 없으면 타임리프 오류
    //                          ㄴ> 순서에 대해서는 잘 모르겠는데, 타임리프 문법에서 아래 페이지로 이동 했을 때 th:object에 대해 @ModelAttribute 없으면 오류 발생 -> 검증 체크 때문인가?
        return "/login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginModel loginModel, BindingResult bindingResult, HttpServletRequest request,// ){
                        @RequestParam(name = "redirectURL", defaultValue = "/") String redirectURL) {

        if (bindingResult.hasErrors()) {
            log.info("login error =[{}]", bindingResult);
            return "login/loginForm";
        }
//        log.info("loginModel.getMail() = [{}], loginModel.getPassword() = [{}]", loginModel.getMail(), loginModel.getPassword());
        //Member loginMember = loginService.login(loginModel.getMail(), loginModel.getPassword());  // 이메일 로그인 -> 아이디 로그인으로 변경
        Member loginMember = loginService.login(loginModel.getUserId(), loginModel.getPassword());
        log.info("loginMember = [{}]", loginMember);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            log.info("login fail = [{}]", bindingResult);
            return "login/loginForm";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.SESSION_NAME, loginMember);
        session.setMaxInactiveInterval(1800);                                   // 글로벌 설정 말고 세션 생성 할 때 직접 설정 글로벌, set 둘다 defalut 시간은 1800초
        log.info("session = {}", session);
        log.info("session.getAtrribute({} = {})", SessionConst.SESSION_NAME, session.getAttribute(SessionConst.SESSION_NAME));

        return "redirect:" + redirectURL;
//        return "redirect:/map";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();                                                // 요청 세션 삭제
        }
        return "redirect:/";
    }
}
