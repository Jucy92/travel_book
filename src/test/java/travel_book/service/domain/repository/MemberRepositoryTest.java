package travel_book.service.domain.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import travel_book.service.ServiceApplication;
import travel_book.service.WebConfig;
import travel_book.service.config.MemberConfig;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.memoryrepository.MemoryRepository;

import java.lang.annotation.Annotation;

@Transactional        // test 메서드나 클래스에 있는 경우 성공 유무와 상관 없이 자동으로 롤백 한다.
@SpringBootTest(classes = {ServiceApplication.class, MemberConfig.class, WebConfig.class})        // @SpringBootTest를 선언하면 @SpringBootApplication를 찾아가서 거기에 설정되어 있는 설정을 그대로를 가져다가 테스트를 실행한다
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



}
