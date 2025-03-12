package travel_book.service.domain.repository.jpa;

import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.domain.repository.MemberSearchCond;
import travel_book.service.web.login.model.FindIdDto;
import travel_book.service.web.login.model.LoginModel;

import java.util.List;
import java.util.Optional;

public class JpaRepository implements MemberRepository {

    @Override
    public Member save(Member member) {
        return null;
    }

    @Override
    public void update(Member member) {

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
    public Optional<Member> findByMember(String userId) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByCondition(FindIdDto searchModel) {
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
    public List<Member> findAll(Object searchCond) {
        return null;
    }

    @Override
    public Optional<Member> memberInfoFindByUser(String paramId) {
        return Optional.empty();
    }

    @Override
    public <T> T selectOne(String queryId, Object param) {
        return null;
    }
}
