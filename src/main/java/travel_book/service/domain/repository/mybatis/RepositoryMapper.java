package travel_book.service.domain.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberSearchCond;
import travel_book.service.web.login.model.FindIdDto;
import travel_book.service.web.login.model.LoginModel;

import java.util.List;
import java.util.Optional;

@Mapper         // 마이바티스 매핑 XML을 호출해주는 매퍼 인터페이스
public interface RepositoryMapper {

    void save(Member member);

    void update(@Param("updateParam") Member member);   // member->updateParam으로 변경 1개는 바로 쓰면 되지만, 2개 이상일 경우 구분 지어줘야한다
    boolean updatePassword(LoginModel model);

    Optional<Member> findByMail(String mail);                   // 받는 매개 변수를 정해야해서 오버로딩해서 사용 불가
    Optional<Member> findByCondition(FindIdDto findIdDto);  // 여러개 조건으로 조회    -> 이걸로 새로 만드려다 아래 findAll(MemberSearchCond searchCond) 사용해보기로
    Optional<Member> findByMember(String userId);
    long findById(String userId);
    long findByUserId(long id);
    List<Member> findAll();
    List<Member> findAll(MemberSearchCond searchCond);
    Optional<Member> memberInfoFindByUser(String userId);

}
