package travel_book.service.web.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import travel_book.service.domain.login.serivce.LoginService;
import travel_book.service.domain.member.Member;
import travel_book.service.web.login.model.LoginModel;
import travel_book.service.web.login.model.FindIdDto;
import travel_book.service.web.login.model.FindPasswordDto;
import travel_book.service.web.session.SessionConst;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginModel") LoginModel loginModel) { // -> 이게 없으면 타임리프 오류
        //                          ㄴ> th:object에 대해 th:field로 값을 매핑해줘야 하는데 loginModel이 없어서 오류
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

    @GetMapping("/login/search_id")
    public String searchId(@ModelAttribute("loginModel") FindIdDto searchModel) {
        return "/login/search-id";
    }

    @PostMapping("/login/search_id")
    public String findId(@Validated @ModelAttribute("loginModel") FindIdDto searchModel, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("findId error =[{}]", bindingResult);
            return "redirect:/login/search-id";
        }
        Member findMember = loginService.findByMail(searchModel); // 타입이 달라서 ModelAttribute에 자동으로 안담기는건가?? Member != LoginSearchModel
        searchModel.setUserId(findMember.getUserId());
        searchModel.setCdt(findMember.getCdt());
        return "/login/search-id-result";
    }

    @GetMapping("/login/search_pwd")
    public String searchPassword(@ModelAttribute("loginModel") FindPasswordDto searchModel) {
        return "/login/search-pwd";
    }

    @PostMapping("/login/search_pwd")
    public String findPassword(@Validated @ModelAttribute("loginModel") FindPasswordDto searchModel, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("find password error={}",bindingResult);
            return "redirect:/login/search-pwd";
        }
        Member result = loginService.findByCondition(searchModel);
        if (result == null) {
            bindingResult.reject("notFound", "일치하는 데이터가 없습니다");
            return "redirect:/login/search-pwd";
        }

        return "/login/search-pwd-result";
    }

    @PostMapping("/login/init_pwd")
    public String initPassword(@ModelAttribute("loginModel") LoginModel loginModel) {
        // 이제 입력 받은 비밀번호 저장하는 처리
        return null;
    }
}
