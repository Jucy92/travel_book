package travel_book.service.domain.login.serivce;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;
//import travel_book.service.domain.member.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;
    public Member login(String email, String password) {
        log.info("loginService [email = [{}], pass = [{}]]", email, password);
        return memberRepository.findByMail(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
