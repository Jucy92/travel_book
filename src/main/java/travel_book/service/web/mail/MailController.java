package travel_book.service.web.mail;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import travel_book.service.web.mail.service.MailService;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    @PostMapping()
    // 이 요청을 신규 회원가입 메일인증 / 기존 회원 비밀번호 찾기 메일 인증 두 곳에서 사용해서 isPasswordFind 라는 값을 하나 더 넘겨줘서 기존 회원 존재 유무 체크 패스
//    public String MailSend(String mail) {     // 데이터 타입 이슈
    public ResponseEntity<Map<String, String>> MailSend(@RequestBody Map<String, Object> paramMap) {

        log.info("넘겨받은 파라미터 값 = {}", paramMap);
        boolean isPasswordFind = Boolean.parseBoolean((String) paramMap.get("isPasswordFind"));
        String mail = paramMap.get("mail").toString();

        Map<String, String> responseMap = new HashMap<>();

        if (!isPasswordFind) {  // true = 비밀번호 찾기 화면 요청, false = 회원가입 메일 인증 화면
            boolean result = mailService.checkedMail(mail);
            if (result) {
                responseMap.put("message", "등록된 메일 주소 입니다.");
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            }
        }
        int number = mailService.sendMail(mail);

        responseMap.put("num", String.valueOf(number));   // 이것도 그냥 눈에 보기 좋게 던져줬는데, 추후에 빼고 서버에서 관리
        responseMap.put("message", "인증번호 전송했습니다.");

        return new ResponseEntity<>(responseMap, HttpStatus.OK);


    }

    @PostMapping("/verifyCode")     // 인증메일 확인 절차
    public ResponseEntity<Map<String, String>> CheckedCode(@RequestBody Map<String, Object> requestMap) {
        log.info("넘겨받은 파라미터 값 = {}", requestMap);
        String userInputCode = (String) requestMap.get("code");
        Map<String, String> responseMap = new HashMap<>();

        boolean isVerified = mailService.verifyCode(userInputCode);
        if (isVerified) {
            responseMap.put("message", "인증에 성공했습니다.");
            responseMap.put("checkedVerify", "true"); // 인증 성공
            return new ResponseEntity<>(responseMap, HttpStatus.ACCEPTED);
        }
        responseMap.put("message", "인증번호가 틀리거나 인증번호가 만료 됐습니다.");
        responseMap.put("checkedVerify", "false"); // 인증 성공
        return new ResponseEntity<>(responseMap, HttpStatus.NOT_FOUND);
    }

}
