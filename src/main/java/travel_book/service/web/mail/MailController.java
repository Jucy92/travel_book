package travel_book.service.web.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travel_book.service.web.mail.service.MailService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MailController {

    private final MailService mailService;
    @PostMapping("/mail")
//    public String MailSend(String mail) {     // 데이터 타입 이슈
    public ResponseEntity<Map<String, String>> MailSend(@RequestParam Map<String, Object> paramMap) {
        log.info("paraMap = {}", paramMap);
        log.info("mail = {}", paramMap.get("mail"));
        String mail = paramMap.get("mail").toString();
        // 여기에 인증에 사용된 이메일인지 체크하는 로직 추가 필요
        // -> true/false  받아서

        boolean result = mailService.checkedMail(mail);
        Map<String, String> resultMap = new HashMap<>();

        if (!result) {
            int number = mailService.sendMail(mail);
            String num = "" + number;
            log.info("MailSend 호출 number = {}", number);
            resultMap.put("num", String.valueOf(number));
            resultMap.put("message", "인증번호 요청했습니다.");

            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }
        resultMap.put("message", "등록된 메일 주소 입니다.");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
    // 제이쿼리로 mailChk 호출해서 가지고 있는 인증 키랑 비교 후 success면 $("ID").hide();    ==> 인증키 어떻게 관리? 그냥 전역변수?
}
