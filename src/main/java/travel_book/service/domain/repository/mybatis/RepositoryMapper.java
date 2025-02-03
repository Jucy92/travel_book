package travel_book.service.domain.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberSearchCond;
import travel_book.service.web.login.model.FindIdDto;
import travel_book.service.web.login.model.LoginModel;

import java.util.List;
import java.util.Optional;

@Mapper // 구현체가 없는 경우 구현체를 만들어서 mybatis XML 호출해줌 -> 공통 기능 사용해보려고 구현체 만들어서 주석처리 / -> SqlSessionFactory, SqlSessionTemplate에 대해서 Mapper 어노테이션이 자동으로 주입 때문에 주석 풀고 Impl에 @Primary 붙여줌
public interface RepositoryMapper {

    void save(Member member);

    void update(@Param("updateParam") Member member);   // member->updateParam으로 변경 1개는 바로 쓰면 되지만, 2개 이상일 경우 구분 지어줘야한다
    int update(String queryId, Object parameter);
    boolean updatePassword(LoginModel model);

    Optional<Member> findByMail(String mail);                   // 받는 매개 변수를 정해야해서 오버로딩해서 사용 불가
    Optional<Member> findByCondition(/*@Param("param") */FindIdDto findIdDto);  // 여러개 조건으로 조회    -> 이걸로 새로 만드려다 아래 findAll(MemberSearchCond searchCond) 사용해보기로
    Optional<Member> findByMember(String userId);
    long findById(String userId);
    long findByUserId(long id);
    List<Member> findAll();
    List<Member> findAll(MemberSearchCond searchCond);
    Optional<Member> memberInfoFindByUser(String userId);

    //@Select("${queryId}")
    <T> T selectOne(@Param("queryId") String queryId, @Param("param") Object param);

    <T> List<T> selectList(@Param("queryId") String queryId, @Param("param") Object param);

}
