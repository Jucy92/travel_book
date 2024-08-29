package travel_book.service.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import travel_book.service.domain.login.serivce.LoginService;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.domain.repository.memoryrepository.MemoryRepository;
import travel_book.service.domain.repository.mybatis.MemberMapper;
import travel_book.service.domain.repository.mybatis.MyBatisRepository;

//@Configuration
@RequiredArgsConstructor
public class MemberConfig {

    private final MemberMapper memberMapper;


    @Bean
    public LoginService loginService() {
        return new LoginService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MyBatisRepository(memberMapper);
//        return new MemoryRepository();
    }
}
