package travel_book.service.domain.repository.memoryrepository;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.domain.repository.MemberSearchCond;
import travel_book.service.web.login.model.FindIdDto;
import travel_book.service.web.login.model.LoginModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class MemoryRepository implements MemberRepository {

    private static Map<Long, Member> store = new ConcurrentHashMap<>();        // 동시성 이슈?로 이거 사용해야한다고 했음 ->  찾아보기

    private static long sequence = 0L;
    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save 호출 - member = [{} {}]",sequence, member);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public void update(Member updateMember) {   // 메일 주소로 -> id 가져와서 메일,이름,번호 다 변경하려고 했는데 안되네 id가 키값이라 map에서 데이터 가져올 수가 없음
        Member findMember = findByMail(updateMember.getMail()).orElseThrow();   // orElseThrow 있으면 벨류값(객체) 없으면 노서치인셉션
        findMember.setName(updateMember.getName());
        findMember.setPassword(updateMember.getPassword());
        findMember.setPhone(updateMember.getPhone());
        // 일단 그냥 메일주소로 이름이나 번호만 변경가능하게 하려고 했으나 아래처럼 해도 되나..?
        findMember.setMail(updateMember.getMail()); // 이렇게 해버려도 상관 없나...? -> mail 중복 x -> findByMail로 찾아오는거기때문에 mail 값이 다르면 값 자체를 못찾음

        log.info("update 호출={}", findMember);
        log.info("List={}",findAll());
    }

    @Override
    public boolean updatePassword(LoginModel model) {
        return false;
    }


    @Override
    public Optional<Member> findByMail(String mail) {       // 회원 리스트에서 전달 받은 mail가 있는지 확인, 있으면 전달 받은 메일 주소 리턴
/*

        log.info("findByMember 호출(1) - mail = [{}]", mail);
        log.info("findByMember 호출(2) - member = [{}]", findAll().stream()
                                                        .filter(m -> m.getMail().equals(mail))
                                                       .findFirst());
*/
        return findAll().stream()
                .filter(m -> m.getMail().equals(mail))
                .findFirst();
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
    public List<Member> findAll() {                         // 메모리에서 Map<key, member> 로 해버리니깐 key 값을 화면에 안넣고 리스트를 찾을 방법이 없음
        log.info("findAll 호출 - store.values = [{}]", store.values());
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Member> findAll(MemberSearchCond searchCond) {      // 변경하면서 바꾸긴 했는데 아직 기능 구현 안함 -> 나중에 이메일 주소, 닉네임으로 게시물 검색할 때 사용
        String mail = searchCond.getMail();
        String name = searchCond.getName();
        log.info("findAll 호출 - store.values = [{}]", store.values());

         return store.values().stream()
                .filter(member -> {
                    if (ObjectUtils.isEmpty(mail)) {
                        return true;
                    }
                    return member.getMail().contains(mail);
                }).filter(member -> {
                    if (ObjectUtils.isEmpty(name)) {
                        return true;
                    }
                    return member.getMail().contains(name);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Member> memberInfoFindByUser(String paramId) {
        return Optional.empty();
    }

    public void clearStore() {
        store.clear();
    }
}
