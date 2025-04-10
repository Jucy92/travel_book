package travel_book.service.web.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import travel_book.service.ServiceApplication;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.web.mail.service.MailService;

@SpringBootTest(classes = {ServiceApplication.class})
//                                    ㄴ> 이걸로 하면 메인이랑 동일   ㄴ> 이걸로 하면 전체 다 끌고 올라와서 중복 (myBatisRepository->.class,memberRepository->MemberConfig)
class MemberControllerTest {


    //@Autowired
    //MemberMapper memberMapper;

    @Autowired
    private ApplicationContext applicationContext;
    //ApplicationContext ac1 = new AnnotationConfigApplicationContext(MemberConfig.class);
    //ApplicationContext ac2 = new AnnotationConfigApplicationContext(MemberMapper.class);


    @Autowired
    MemberRepository memberRepository;
    @MockBean
    private JavaMailSender javaMailSender; // Mail 관련 의존성 Mock 처리
    @Autowired
    private MailService mailService;

    @Test
    void equals() {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }
    }
    @Test
    void add() {
        Member member = new Member();
        member.setName("테스트1");
        member.setEmail("test1@test.com");
        member.setUserId("test1");
        member.setPassword("1234");
        memberRepository.save(member);
        Assertions.assertThat(memberRepository.findAll()).hasSize(1);

    }

}