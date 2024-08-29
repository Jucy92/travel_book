package travel_book.service;

import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import travel_book.service.domain.member.Member;

import java.util.Optional;


@SpringBootTest
class ServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testBean() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);	// TestBean 빈을 스프링 컨테이너에 등록
//		ac.getBean("member", TestBean.class);
		ac.getBeanDefinitionNames();
	}

	static class TestBean {
		@Autowired(required = false)				// 해당 빈이 없으면 실행자체를 하지 않는다.
		public void setNoBean1(Member noBean1) {	// 스프링 컨테이너에서 member라는 빈을 가져와서 주입 시도 => 등록된 빈이 아니라 실패
			System.out.println("noBean1 = " + noBean1);
		}
		@Autowired									// 
		public void setNoBean2(@Nullable Member noBean2) {
			System.out.println("noBean2 = " + noBean2);
		}
		@Autowired
		public void setNoBean3(Optional<Member> noBean3) {	// 스프링 컨테이너에서 member라는 빈을 가져와서 주입 시도 => 등록된 빈이 아니라 실패
			System.out.println("noBean3 = " + noBean3);
		}
	}

}
