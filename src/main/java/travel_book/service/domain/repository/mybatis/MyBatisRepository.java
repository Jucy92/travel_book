package travel_book.service.domain.repository.mybatis;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.domain.repository.MemberSearchCond;
import travel_book.service.web.login.model.FindIdDto;
import travel_book.service.web.login.model.LoginModel;
import travel_book.service.web.profile.dto.ProfileData;

import java.util.List;
import java.util.Optional;

@Repository
@Primary    // 기본 빈으로 지정(Repository 종류 중)
@RequiredArgsConstructor
public class MyBatisRepository implements MemberRepository {

    private final RepositoryMapper repositoryMapper;

    @Override
    public Member save(Member member) {
        repositoryMapper.save(member);
        return member;      // 이건 의미 없는데 Member 타입이라 어찌됐든 리턴해야해서 본인꺼 다시 보내주는건가? -> 흠 리턴안해줘도 될 거 같은데.. itemservice-db 보고 따라해서..
    }

    @Override
    public void update(Member member) {
        repositoryMapper.update(member);
    }

    @Override
    public boolean updatePassword(LoginModel model) {  // 다형성(메모리, JPA, 마이바티스) 때문에 하나씩 계속 생성하는데.. 동적으로 처리해야하나..
        return repositoryMapper.updatePassword(model);
    }

    @Override
    public Optional<Member> findByMail(String mail) {
        return repositoryMapper.selectOne("findByMail", mail);
    }

    public Optional<Member> findByCondition(FindIdDto findIdDto) {
        //return repositoryMapper.findByCondition(findIdDto);
        return repositoryMapper.selectOne("findByCondition", findIdDto);
    }

    @Override
    public Optional<Member> findMemberByUserId(String userId) {
        return repositoryMapper.selectOne("findMemberByUserId", userId);
    }

    @Override
    public long findIdByUserId(String userId) {
        long findById = (long) repositoryMapper.selectOne("findIdByUserId", userId).orElse(0L);
        System.out.println("findIdByUserId = " + findById);
        return findById;
//        return 0;
    }


    @Override
    public long findByUserId(long id) {
        return (long) repositoryMapper.selectOne("findByUserId", id).orElse(0L);
    }


    @Override
    public List<Member> findAll() {     // 테스트에서 사용으로 로직 변경
        //return repositoryMapper.findAll();
        return repositoryMapper.selectList("findAll");
    }

    @Override
    public List<Member> findAll(Object searchCond) {
        return repositoryMapper.selectList("findAll", searchCond);
    }

    public Optional<Member> memberInfoFindByUser(String userId) {      // 넘겨 받은 userId or name 가지고 id 값 찾기
        return repositoryMapper.memberInfoFindByUser(userId);
    }

    @Override
    public ProfileData findProfileDataByUserId(String userId) {
        return (ProfileData) repositoryMapper.selectOne("findProfileDataByUserId", userId).orElse(null);
    }
    
    /*
    // 여기가 서비스 로직인데 여기서 dao 기능을 수행할 수 없지 당연히
    public Member selectOne(String queryId, Object param) { 
        System.out.println("selectOne 호출");
        Object obj = repositoryMapper.selectOne(queryId, param);// xml 에서 어떻게 처리를 할까
        Member member = (Member) obj;
        return member;
    }
    */
    

}
