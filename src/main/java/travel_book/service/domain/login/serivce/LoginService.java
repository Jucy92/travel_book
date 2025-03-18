package travel_book.service.domain.login.serivce;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import travel_book.service.domain.crypto.service.EncryptionService;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;
import travel_book.service.web.login.model.FindIdDto;
import travel_book.service.web.login.model.LoginModel;

import java.util.List;
//import travel_book.service.domain.member.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;    // = 회원 서비스(Repository == Service) 용어 혼동 x
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
        log.info("login 데이터 [userId = [{}], pass = [{}]]", userId, password);
        String encrypt = encryptionService.encrypt(password);
        String decrypt = encryptionService.decrypt(encrypt);
        log.info("login.loginService 암호화={}, 복호화={}", encrypt, decrypt);  // 적용은 안하고 기능만 만들어놓음
        return memberRepository.findMemberByUserId(userId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public Member findByMail(FindIdDto searchModel) {
        log.info("findByMail.searchModel ={}", searchModel);
        //List<Member> Members = memberRepository.findAll();// 메일 찾기에서 회원 리스트 담아오나 테스트
        List<Member> Members = memberRepository.findAll(searchModel);// 메일 찾기에서 회원 리스트 담아오나 테스트
        log.info("MemberFindAll={}", Members);
        return memberRepository.findByMail(searchModel.getMail()).orElse(null);

        //return memberRepository.findByMail(searchModel.getMail()).orElseThrow();
        // 이러면 무슨 오류가 발생할까?? 널포인터?? 이 오류를 캐치로 잡아서 일치하는 이메일 주소 없다고 알려주기  => 발생하는 예외 NoSuchElementException
    }

    public Member findByCondition(FindIdDto searchModel) {
        log.info("findByCondition.searchModel={}", searchModel);
        return memberRepository.findByCondition(searchModel).orElse(null);
    }

    public boolean updatePassword(LoginModel loginModel) {
        log.info("updatePassword.loginModel={}", loginModel);
        String encrypt = encryptionService.encrypt(loginModel.getPassword());
        String decrypt = encryptionService.decrypt(encrypt);
        log.info("updatePassword.loginService 암호화={}, 복호화={}", encrypt, decrypt);  // 적용은 안하고 기능만 만들어놓음
        return memberRepository.updatePassword(loginModel);    // 여기서 넣어버려도 암호화 한걸로 넣어도 되긴하는데~
    }
}
