package travel_book.service.domain.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberSearchCond;

import java.util.List;
import java.util.Optional;

@Mapper         // 마이바티스 매핑 XML을 호출해주는 매퍼 인터페이스
public interface MemberMapper {

    void save(Member member);

    void update(@Param("updateParam") Member member);

    Optional<Member> findByMail(String mail);
    Optional<Member> findByUserId(String userId);

    List<Member> findAll();
    List<Member> findAll(MemberSearchCond searchCond);

    Optional<Member> memberInfoFindByUser(String usermId);

}
