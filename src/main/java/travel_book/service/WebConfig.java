package travel_book.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import travel_book.service.web.interceptor.LogInterceptor;
import travel_book.service.web.interceptor.LoginCheckInterceptor;

@Component  // 컨테이너에 등록 안하면 실행이 안되네
public class WebConfig implements WebMvcConfigurer {    // 스프링 인터셉터 사용하기 위해서 implements WebMvcConfigurer 설정
    @Override
    public void addInterceptors(InterceptorRegistry registry) { // 인터셉터 추가
        registry.addInterceptor(new LogInterceptor())           // 로그
                .order(1)
                .addPathPatterns("/**") // /하위 전부 다
                .excludePathPatterns("/css/**", "*.ico", "/error");
        /*
        registry.addInterceptor(new LoginCheckInterceptor())        // LoginCheckInterceptor 설정 파일 읽어와서 적용
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/login", "/", "/members/add", "/logout", "/mail");
        */
    }


}
