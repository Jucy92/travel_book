package travel_book.service.config;


import lombok.RequiredArgsConstructor;
import travel_book.service.domain.login.serivce.LoginService;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.domain.repository.mybatis.RepositoryMapper;
import travel_book.service.domain.repository.mybatis.MyBatisRepository;

//@Configuration
@RequiredArgsConstructor
public class MemberConfig {

    /*
    private final RepositoryMapper repositoryMapper;



    //@Bean
    public LoginService loginService() {
        return new LoginService(memberRepository());
    }

    //@Bean     // $$Spring$$ 없애려고 이거 지우니깐 컴파일 오류 memberRepository 찾아서 Autowired 써주기
    public MemberRepository memberRepository() {
        return new MyBatisRepository(repositoryMapper);
//        return new MemoryRepository();
    }
    */
}
