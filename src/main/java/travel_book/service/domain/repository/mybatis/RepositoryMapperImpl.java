package travel_book.service.domain.repository.mybatis;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberSearchCond;
import travel_book.service.web.login.model.FindIdDto;
import travel_book.service.web.login.model.LoginModel;

import java.util.List;
import java.util.Optional;

@Primary
@Repository
@RequiredArgsConstructor
public class RepositoryMapperImpl implements RepositoryMapper{


    private final SqlSession sqlSession;
    @Override
    public void save(Member member) {

    }

    @Override
    public void update(Member member) {

    }

    @Override
    public int update(String queryId, Object parameter) {
        return sqlSession.update(queryId, parameter);
    }

    @Override
    public boolean updatePassword(LoginModel model) {
        return false;
    }

    @Override
    public Optional<Member> findByMail(String mail) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByCondition(FindIdDto findIdDto) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByMember(String userId) {
        return Optional.empty();
    }

    @Override
    public long findById(String userId) {
        return 0;
    }

    @Override
    public long findByUserId(long id) {
        return 0;
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public List<Member> findAll(MemberSearchCond searchCond) {
        return null;
    }

    @Override
    public Optional<Member> memberInfoFindByUser(String userId) {
        return Optional.empty();
    }

    @Override
    public <T> T selectOne(String queryId, Object param) {
        return sqlSession.selectOne(queryId, param);
    }

    @Override   // select One, List 다 sqlSession 기능 -> DAO 인터페이스랑 상관 없음 -> 하지만 서비스에서 사용 할 때는 인터페이스를 바라보고 있기 때문에 필요
    public <T> List<T> selectList(String queryId, Object param) {
        return sqlSession.selectList(queryId, param);
    }
}
