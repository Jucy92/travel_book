package travel_book.service.domain.login.serivce;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import travel_book.service.domain.crypto.service.EncryptionService;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.web.login.model.FindIdDto;
//import travel_book.service.domain.member.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;
    private final EncryptionService encryptionService;
    
    /*
    public Member login(String email, String password) {        // 이메일 로그인 방식에서 아이디 로그인 방식으로 변경
        log.info("loginService [email = [{}], pass = [{}]]", email, password);
        return memberRepository.findByMail(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
    */
    public Member login(String userId, String password) {
        log.info("loginService [userId = [{}], pass = [{}]]", userId, password);
        String encrypt = encryptionService.encrypt(password);
        String decrypt = encryptionService.decrypt(encrypt);
        log.info("loginService 암호화={}, 복호화={}", encrypt, decrypt);  // 적용은 안하고 기능만 만들어놓음
        return memberRepository.findByMember(userId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public Member findByMail(FindIdDto searchModel) {
        log.info("searchModel ={}", searchModel);
        return memberRepository.findByMail(searchModel.getMail()).orElseThrow();
        // 이러면 무슨 오류가 발생할까?? 널포인터?? 이 오류를 캐치로 잡아서 일치하는 이메일 주소 없다고 알려주기  => 발생하는 예외 NoSuchElementException
    }

    public Member findByCondition(FindIdDto searchModel) {
        log.info("searchModel={}", searchModel);
        return memberRepository.findByCondition(searchModel).orElse(null);
    }
}
