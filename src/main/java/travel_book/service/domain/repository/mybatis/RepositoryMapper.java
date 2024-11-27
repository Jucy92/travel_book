package travel_book.service.domain.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberSearchCond;

import java.util.List;
import java.util.Optional;

@Mapper         // 마이바티스 매핑 XML을 호출해주는 매퍼 인터페이스
public interface RepositoryMapper {

    void save(Member member);

    void update(@Param("updateParam") Member member);   // member->updateParam으로 변경 1개는 바로 쓰면 되지만, 2개 이상일 경우 구분 지어줘야한다

    Optional<Member> findByMail(String mail);
    Optional<Member> findByMember(String userId);
    long findById(String userId);
    long findByUserId(long id);
    List<Member> findAll();
    List<Member> findAll(MemberSearchCond searchCond);
    Optional<Member> memberInfoFindByUser(String userId);

}
