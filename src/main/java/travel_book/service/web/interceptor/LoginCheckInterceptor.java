package travel_book.service.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import travel_book.service.web.session.SessionConst;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 {}", requestURI);
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConst.SESSION_NAME) == null) {
            log.info("미인증 사용자 로그인 접근");
            log.info("요청 URI = {}", requestURI);
            response.sendRedirect("/login?redirectURL=" + requestURI);
                                            //    ㄴ> redirectURL 이 명칭이랑 받는쪽-컨트롤러(LoginController-login) @RequestParam 매개변수 명이랑 일치해야한다
            return false;
        }
        return true;
    }
}
