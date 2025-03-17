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
@Repository   // MyBatis의 자동 프록시가 아닌 직접 구현체 사용
@RequiredArgsConstructor
public class RepositoryMapperImpl implements RepositoryMapper{


    private final SqlSession sqlSession;
    @Override
    public void save(Member member) {
        sqlSession.insert("RepositoryMapper.save", member); // RepositoryMapper 인터페이스의 save(Member member)을 수동으로 호출
        // 인터페이스에 @Mapper가 붙는 경우 자동으로 프록시 기능을 통해 구현체 생성해주지만,
        // 여기서는 DAO 공통으로 호출할 수 있도록 queryId를 넘겨주고 받기 위해 직접 구현체 생성 했기에 위와같이 수동으로 호출 해줘야한다.
    }

    @Override
    public void update(Member member) {
        sqlSession.update("RepositoryMapper.update", member);
    }

    /*
    public int update(String queryId, Object parameter) {   // insert, update 확장 가능성 있음? Member 테이블인데? 다른 테이블을 여기서 저장하거나 변경할 일이 있을까?
        return sqlSession.update(queryId, parameter);       // ㄴ> 사용할 일이 없을거 같아서 공통으로 빼주는 작업 추후로 미룸 -> 부분 insert, update 시 필요해보임
    }
    */

    @Override
    public boolean updatePassword(LoginModel model) {
        int result = sqlSession.update("updatePassword", model);
        return result > 0;  // 업데이트된 행이 1개 이상이면 true 반환
    }

    @Override
    public Optional<Member> memberInfoFindByUser(String userId) {
        return Optional.empty();
    }

    @Override
    public <T> Optional<T> selectOne(String queryId, Object param) {
        // 마이바티스 xml 파일에 중복으로있는 queryId를 여기서 동적으로 호출하면 중복 오류 발생한다. (@Mapper 통해서 자동 생성되거나, 직접 생성한 거나 둘 다 한 곳에 값이 저장되어있는듯..?)
        // ex) findById => Member(findUserIdById로 변경)에도 있고, travel 에도 있었음          ㄴ> 직접 명시하기 떄문에 상관 없는데,  ㄴ> queryId로 검색하기 때문에 중복 오류 발생하는 듯
        return Optional.ofNullable(sqlSession.selectOne(queryId, param));
    }

    @Override   // select One, List 다 sqlSession 기능 -> DAO 인터페이스랑 상관 없음 -> 하지만 서비스에서 사용 할 때는 인터페이스를 바라보고 있기 때문에 필요
    public <T> List<T> selectList(String queryId, Object param) {
        return sqlSession.selectList(queryId, param);
    }
    @Override
    public <T> List<T> selectList(String queryId) {
        return sqlSession.selectList(queryId);
    }
}
