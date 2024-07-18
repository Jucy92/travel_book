package travel_book.service.web.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travel_book.service.web.mail.service.MailService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MailController {

    private final MailService mailService;
    @PostMapping("/mail")
//    public String MailSend(String mail) {     // 데이터 타입 이슈
    public String MailSend(@RequestParam Map<String, Object> paramMap) {
        log.info("paraMap = {}", paramMap);
        log.info("mail = {}", paramMap.get("mail"));
        String mail = paramMap.get("mail").toString();
        int number = mailService.sendMail(mail);
        String num = "" + number;
        log.info("MailSend 호출 number = {}", number);
        return num;
    }
    // 제이쿼리로 mailChk 호출해서 가지고 있는 인증 키랑 비교 후 success면 $("ID").hide();    ==> 인증키 어떻게 관리? 그냥 전역변수?
}
