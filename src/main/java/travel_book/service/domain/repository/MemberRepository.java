package travel_book.service.domain.repository;

import travel_book.service.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    public Member save(Member member);

    void update(Member member);

//    public Optional<Member> findByMailToId(String mail);

    public Optional<Member> findByMail(String mail);                    // 쓸 일이 없네...? 그냥 메일 기능으로 한번 바꿔보긴했는데..  => 로그인 기능에서 사용 했었네 
    public Optional<Member> findByUserId(String userId);                // 지금은 userId로 로그인 변경 

    public List<Member> findAll();                                      // 메모리에서 이메일로 저장 데이터 찾을 때 필요...
    public List<Member> findAll(MemberSearchCond searchCond);
    public Optional<Member> memberInfoFindByUser(String paramId);       // 넘겨 받은 userId or name 가지고 id 값 찾기




}
