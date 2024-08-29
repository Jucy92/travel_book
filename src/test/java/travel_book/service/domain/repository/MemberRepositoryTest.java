package travel_book.service.domain.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import travel_book.service.config.MemberConfig;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.memoryrepository.MemoryRepository;

import java.lang.annotation.Annotation;

@Transactional        // test 메서드나 클래스에 있는 경우 성공 유무와 상관 없이 자동으로 롤백 한다.
@SpringBootTest        // @SpringBootTest를 선언하면 @SpringBootApplication를 찾아가서 거기에 설정되어 있는 설정을 그대로를 가져다가 테스트를 실행한다
public class MemberRepositoryTest {

//    @Autowired
	MemberRepository memberRepository;

	@AfterEach
    void afterEach() {
		//MemoryItemRepository 의 경우 제한적으로 사용
		if (memberRepository instanceof MemoryRepository) {
			((MemoryRepository) memberRepository).clearStore();
		}
	}

	@Test
	void save() {
		Member member = new Member();
        member.setMail("juchjee@naver.com");
        member.setName("주찬양");
        member.setPassword("1234");

        memberRepository.save(member);
	}

	@Test
	@DisplayName("스프링 빈 테스트")
	void springBeanTest() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MemberConfig.class);
		System.out.println(ac.getBeanDefinitionNames());
	}



}
