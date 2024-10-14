package travel_book.service.domain.member;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import travel_book.service.ServiceApplication;
import travel_book.service.WebConfig;
import travel_book.service.config.MemberConfig;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.domain.repository.mybatis.MemberMapper;
import travel_book.service.web.home.HomeController;
import travel_book.service.web.mail.service.MailService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional        // test 메서드나 클래스에 있는 경우 성공 유무와 상관 없이 자동으로 롤백 한다.
//@SpringBootTest(classes = {ServiceApplication.class, MemberConfig.class, WebConfig.class})
@SpringBootTest
@ContextConfiguration(classes = {ServiceApplication.class, MemberConfig.class, WebConfig.class})
@Slf4j
public class MemberServiceTest {
    @Autowired  // 컨테이너에 있더라도 여기서 사용하려면(주입) 사용해야한다
    MemberRepository memberRepository;
    @MockBean
    private JavaMailSender javaMailSender; // Mail 관련 의존성 Mock 처리
    @Autowired
    private MailService mailService;

    @Test
    @DisplayName("회원 정보 조회")
    void testFindMembers() {
        //log.info("JavaMailSender: {}", javaMailSender);
        //log.info("MailService: {}", mailService);

        //log.info("MemberRepository: {}", memberRepository); // MemberService가 null인지 확인
        //log.info("MemberRepository: {}", memberService.getMemberRepository()); // MemberRepository가 null인지 확인

        // Given: 테스트용 데이터를 생성하여 저장
        Member member1 = new Member();
        member1.setMail("test100@naver.com");
        member1.setName("주찬양");
        member1.setUserId("test100");
        member1.setPassword("1234");
        Member member2 = new Member();
        member2.setMail("test200@naver.com");
        member2.setName("양");
        member2.setUserId("test200");
        member2.setPassword("1234");
        //memberRepository.getClass().getSimpleName();
        memberRepository.save(member1);
        memberRepository.save(member2);


        List<Member> memeberList = memberRepository.findAll();

        assertThat(memeberList).hasSize(4); // 2가 맞는데 일부러 4로 검증 실패 만듬
    }
}
