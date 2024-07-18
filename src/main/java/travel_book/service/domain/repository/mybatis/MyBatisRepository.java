package travel_book.service.domain.repository.mybatis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.domain.repository.MemberSearchCond;

import java.util.List;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
public class MyBatisRepository implements MemberRepository {

    private final MemberMapper memberMapper;
    @Override
    public Member save(Member member) {
        memberMapper.save(member);
        return member;      // 이건 의미 없는데 Member 타입이라 어찌됐든 리턴해야해서 본인꺼 다시 보내주는건가? -> 흠 리턴안해줘도 될 거 같은데.. itemservice-db 보고 따라해서..
    }

    @Override
    public void update(Member member) {
        memberMapper.update(member);
    }

    @Override
    public Optional<Member> findByMail(String mail) {
        return memberMapper.findByMail(mail);
    }

    @Override
    public List<Member> findAll() {     // 사용 안할 예정
        return null;
    }

    @Override
    public List<Member> findAll(MemberSearchCond searchCond) {
        return memberMapper.findAll(searchCond);
    }

    public Optional<Member> memberInfoFindByUser(String userId) {      // 넘겨 받은 userId or name 가지고 id 값 찾기
        return memberMapper.memberInfoFindByUser(userId);
    }

}
