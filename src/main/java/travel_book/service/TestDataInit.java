package travel_book.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;

@RequiredArgsConstructor
@Slf4j
public class TestDataInit {

    private final MemberRepository memberRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("test data init");
        Member member = new Member();
        member.setEmail("juchjee@naver.com");
        member.setName("주찬양");
        member.setPassword("1234");
        //member.setCdt(LocalDateTime.now());   서비스 로직에서 처리하는게 맞는거 같아서 Mapper쪽에서 처리

        memberRepository.save(member);
    }
}
