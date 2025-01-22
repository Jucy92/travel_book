package travel_book.service.domain.repository.mybatis;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.domain.repository.MemberSearchCond;
import travel_book.service.web.login.model.FindIdDto;
import travel_book.service.web.login.model.LoginModel;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
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
        return repositoryMapper.findByMail(mail);
    }

    public Optional<Member> findByCondition(FindIdDto findIdDto) {
        return repositoryMapper.findByCondition(findIdDto);
    }

    @Override
    public Optional<Member> findByMember(String userId) {
        return repositoryMapper.findByMember(userId);
    }

    @Override
    public long findById(String userId) {
        return repositoryMapper.findById(userId);
    }


    @Override
    public long findByUserId(long id) {
        return repositoryMapper.findByUserId(id);
    }


    @Override
    public List<Member> findAll() {     // 테스트에서 사용으로 로직 변경
        return repositoryMapper.findAll();
    }

    @Override
    public List<Member> findAll(MemberSearchCond searchCond) {
        return repositoryMapper.findAll(searchCond);
    }

    public Optional<Member> memberInfoFindByUser(String userId) {      // 넘겨 받은 userId or name 가지고 id 값 찾기
        return repositoryMapper.memberInfoFindByUser(userId);
    }

}
