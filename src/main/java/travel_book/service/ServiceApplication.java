package travel_book.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import travel_book.service.config.MemberConfig;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.domain.repository.mybatis.MyBatisRepository;

@Slf4j
@SpringBootApplication//(scanBasePackages = {"travel_book.service.web", "travel_book.service.domain.member"})	// scanBasePackages를 사용해서 나머지 빼버렸더니 빈등록 되어있던게 다 빠지고 컨피그 파일만 읽어드림
//@Import({MemberConfig.class, WebConfig.class})		// 이거 안풀어도 @Config 보고 컨테이너 컴포넌트에 등록되는데 위에 scanBasePackages랑 쓰기 위해서 선언
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}


	@Bean
	@Profile("init")			// application.properties에 설정되어있는 프로필만 실행된다. / 환경별로 실행 안되게 설정 가능
	public TestDataInit testDataInit(MemberRepository memberRepository) {	// profile이 local일 떄만 실행 -> 읽어드리면서 TestDataInit 생성(호출)
		return new TestDataInit(memberRepository);
	}

}
