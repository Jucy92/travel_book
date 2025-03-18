package travel_book.service.domain.repository;

import travel_book.service.domain.member.Member;
import travel_book.service.web.login.model.FindIdDto;
import travel_book.service.web.login.model.LoginModel;
import travel_book.service.web.profile.dto.ProfileData;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    public Member save(Member member);

    void update(Member member);
    boolean updatePassword(LoginModel model);

//    public Optional<Member> findByMailToId(String mail);

    public Optional<Member> findByMail(String mail);                    // 쓸 일이 없네...? 그냥 메일 기능으로 한번 바꿔보긴했는데..  => 로그인 기능에서 사용 했었네 
    public Optional<Member> findMemberByUserId(String userId);                // userId로 Member 정보 다 가져오기
    public Optional<Member> findByCondition(FindIdDto searchModel);
    public long findIdByUserId(String userId);                                // userId로 id 값만 찾는 쿼리 -> member에 id만 나옴... 근데 이게 성능에 그렇게 지장이 있을까..? 쿼리문은 똑같이 실행되는데.. member 다 가져오는거나 id 하나 가져오는거나..
    public long findByUserId(long id);                                  // id로 userId 찾기

    public List<Member> findAll();                                      // 메모리에서 이메일로 저장 데이터 찾을 때 필요...
    public List<Member> findAll(Object searchCond);
    public Optional<Member> memberInfoFindByUser(String paramId);       // 넘겨 받은 userId or name 가지고 id 값 찾기
    public ProfileData findProfileDataByUserId(String userId);          // 이렇게 받으면 VO로 불변처리해도 될듯..?
}
