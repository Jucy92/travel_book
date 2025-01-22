package travel_book.service.web.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import travel_book.service.domain.crypto.service.EncryptionService;
import travel_book.service.domain.login.serivce.LoginService;
import travel_book.service.domain.member.Member;
import travel_book.service.web.login.model.LoginModel;
import travel_book.service.web.login.model.FindIdDto;
import travel_book.service.web.login.model.FindPasswordDto;
import travel_book.service.web.session.SessionConst;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/login/search-id")
    public String searchId(@ModelAttribute("loginModel") FindIdDto searchModel, HttpSession session, Model model) {
        // 세션은 각(브라우저) HTTP1.1 요청에 따라서 생성되기 때문에, key 값을 알아도 조회 불가

        BindingResult bindingResult = (BindingResult) session.getAttribute("org.springframework.validation.BindingResult.loginModel");

        if (bindingResult != null) {
            model.addAttribute("org.springframework.validation.BindingResult.loginModel", bindingResult);
            session.removeAttribute("org.springframework.validation.BindingResult.loginModel");
        }

        return "/login/search-id";
    }

    @PostMapping("/login/search-id")
    public String findId(@Validated @ModelAttribute("loginModel") FindIdDto searchModel, BindingResult bindingResult, HttpServletRequest request) {
        /**
         * 원래 그냥 RedirectAttributes redirectAttributes 파라미터로 받고     // 그냥 리다이렉트 하면, validated, loginModel 데이터 다 사라져서 RedirectAttributes 사용
         * redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginModel", bindingResult);
         * redirectAttributes.addFlashAttribute("loginModel", searchModel);  
         * Get 화면에서 loginModel 조회되는데 bindingResult 전달이 안돼서
         * 세션으로 값 저장하고 넘겨주고 받아서 처리
         */
        HttpSession session = request.getSession();

        if (bindingResult.hasErrors()) {
            log.info("findId bindingResult error =[{}]", bindingResult);
            session.setAttribute("org.springframework.validation.BindingResult.loginModel", bindingResult);

            return "redirect:/login/search-id"; // 리다하면서 loginModel 데이터 없이 오류메시지만 세션에 담아서 전달
        }
        try {
            Member findMember = loginService.findByMail(searchModel); // 만약 회원이 조회되지 않으면 예외 터뜨리게 해놓음 -> 나중에 공통 예외 페이지 생성
            log.info("findMember={}", findMember);
            searchModel.setUserId(findMember.getUserId());
            searchModel.setCdt(findMember.getCdt());
            session.setAttribute("org.springframework.validation.BindingResult.loginModel", bindingResult);
            session.setAttribute("loginModel", searchModel);

            return "redirect:/login/search-id-result";
        } catch (Exception e) {
            bindingResult.reject("notFound", "일치하는 데이터가 없습니다.");
            session.setAttribute("org.springframework.validation.BindingResult.loginModel", bindingResult);

            return "redirect:/login/search-id";
        }
    }

    @GetMapping("/login/search-id-result")
    public String searchIdResult(HttpSession session, Model model) {
        // 세션은 각(브라우저) HTTP1.1 요청에 따라서 생성되기 때문에, key 값을 알아도 조회 불가
        BindingResult bindingResult = (BindingResult) session.getAttribute("org.springframework.validation.BindingResult.loginModel");
        FindIdDto loginModel = (FindIdDto) session.getAttribute("loginModel");

        if (bindingResult != null && loginModel != null) {
            model.addAttribute("org.springframework.validation.BindingResult.loginModel", bindingResult);
            model.addAttribute("loginModel", loginModel);     //@ModelAttribute("loginModel") 을 통해 add 되어 있음
            session.removeAttribute("org.springframework.validation.BindingResult.loginModel");
            session.removeAttribute("loginModel");
        }
        // 새로고침하면 그냥 빈 값 페이지 뜨는데.. 이게 맞을까.. 아니면 다른 사이트로 리다이렉트 보내주는게 맞을까..

        return "/login/search-id-result";
    }

    @GetMapping("/login/search-pwd")
    public String searchPassword(@ModelAttribute("loginModel") FindPasswordDto searchModel, HttpSession session, Model model) {


        BindingResult bindingResult = (BindingResult) session.getAttribute("org.springframework.validation.BindingResult.loginModel");
        log.info("bindingResult {}", bindingResult);
        //log.info("loginModel {}", searchModel);

        if (bindingResult != null) {
            model.addAttribute("org.springframework.validation.BindingResult.loginModel", bindingResult);
            session.removeAttribute("org.springframework.validation.BindingResult.loginModel");
        }

        return "/login/search-pwd";
    }

    @PostMapping("/login/search-pwd")
    public String findPassword(@Validated @ModelAttribute("loginModel") FindPasswordDto searchModel,
                               BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        log.info("loginModel={}", searchModel);

        if (bindingResult.hasErrors()) {
            log.info("find password error={}", bindingResult);
            // 이거만 넘어가면 깔끔해질텐데.. 리다이렉트로 binding 안넘어가서 그 부분만 다 세션으로 넘겨서 처리
            //redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginModel", bindingResult); 
            redirectAttributes.addFlashAttribute("loginModel", searchModel);
            if (!searchModel.getMail().isEmpty()) {
                redirectAttributes.addFlashAttribute("showEmailVerifyBtn", true);
            }
            log.info("redirectAttributes: {}", redirectAttributes.getFlashAttributes());
            session.setAttribute("org.springframework.validation.BindingResult.loginModel", bindingResult);

            return "redirect:/login/search-pwd";
        }
        Member result = loginService.findByCondition(searchModel);  // 모델에 이메일이 없네? 상속받았는데?
        if (result == null) {
            bindingResult.reject("notFound", "일치하는 데이터가 없습니다.");
            session.setAttribute("org.springframework.validation.BindingResult.loginModel", bindingResult);
            return "redirect:/login/search-pwd";
        }
        session.setAttribute("org.springframework.validation.BindingResult.loginModel", bindingResult);
        redirectAttributes.addFlashAttribute("loginModel", searchModel);

        return "redirect:/login/search-pwd-result";
    }

    @GetMapping("/login/search-pwd-result")
    public String searchPasswordResult(HttpSession session, Model model) {
        // 세션은 각(브라우저) HTTP1.1 요청에 따라서 생성되기 때문에, key 값을 알아도 조회 불가
        BindingResult bindingResult = (BindingResult) session.getAttribute("org.springframework.validation.BindingResult.loginModel");

        if (bindingResult != null) {
            model.addAttribute("org.springframework.validation.BindingResult.loginModel", bindingResult);
            session.removeAttribute("org.springframework.validation.BindingResult.loginModel");
        }
        // 화면에서 <p id="userId" th:text="${loginModel.userId}"></p> 이 부분 값이 있으면 보여주는걸로 변경 쟤때문에 값 없어서 오류 발생

        return "/login/search-pwd-result";
    }

    @ResponseBody
    @PostMapping("/login/init-password")
    public ResponseEntity<Map<String, Object>> initPassword(@RequestBody LoginModel loginModel) {
        // 이제 입력 받은 비밀번호 저장하는 처리 -> Map 말고 DTO 통해서 처리해보기
        log.info("initPassword.loginModel={}", loginModel);

        Map<String, Object> responseMap = new HashMap<>();
        try {
            boolean isUpdate = loginService.updatePassword(loginModel);
            log.info("initPassword.isUpdate={}", isUpdate);
            if (!isUpdate) {
                responseMap.put("message", "변경에 실패 했습니다.");
                responseMap.put("status", false);
                return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
            }
            responseMap.put("message", "정상적으로 변경 했습니다.");
            responseMap.put("status", true);

            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (Exception e) {
            responseMap.put("message", "서버 오류가 발생했습니다.");
            responseMap.put("status", false);
            return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
