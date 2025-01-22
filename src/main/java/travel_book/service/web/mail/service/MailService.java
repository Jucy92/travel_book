package travel_book.service.web.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import travel_book.service.domain.repository.MemberRepository;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "jcy92@gmail.com";
    @Autowired
    private HttpSession session;
    private final int TIMEOUT_MINUTES = 5;
    private static int number;

    public static void createNumber() {
        number = (int) (Math.random() * (90000)) + 100000;  // (int) Math.random() * (최대값-최소값+1) + 최소값
    }

    public MimeMessage CreateMail(String mail){
        createNumber();
        // 재요청 시 같은 변수에 값 그대로 덮어씀
        session.setAttribute("verificationCode", number);
        session.setAttribute("verificationTime", LocalDateTime.now());
        MimeMessage message = javaMailSender.createMimeMessage();

        try{
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "이메일 인증 코드 보내드립니다. (코드는 5분 후 만료 됩니다.)" + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body, "UTF-8", "html");

        }  catch(MessagingException e){
            log.info("MessagingException = {}", e);
            e.printStackTrace();
        }
        return message;
    }

    public int sendMail(String mail) {
        MimeMessage message = CreateMail(mail);     // 인증번호 + 메일 내용 생성
        log.info("생성된 코드번호={}", session.getAttribute("verificationCode"));
        log.info("생성된 시간={}", session.getAttribute("verificationTime"));
        javaMailSender.send(message);   // 인증번호 메일 통해서 발송

        return number;
    }

    // 메일 계정 유무 확인
    public boolean checkedMail(String mail) {
        //Member member = memberRepository.findByMail(mail).orElse(null);
        return memberRepository.findByMail(mail).isPresent();   // Optional 기능 (isPresent 값이 있으면 true 없으면 false / isEmpty 값이 없으면 true)
    }
    // 인증번호 유효기간 확인 (세션으로 관리)
    public boolean verifyCode(String userInputCode) {
        /*
        // 세션에 담긴 값 찍어봄
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attributeValue = session.getAttribute(attributeName);
            System.out.println("verifyCode -> " +attributeName + ": " + attributeValue);
        }
        */
        // 세션에서 인증번호와 발급 시간 가져오기
        String storedCode = String.valueOf(session.getAttribute("verificationCode"));
        LocalDateTime storedTime = (LocalDateTime) session.getAttribute("verificationTime");
        // 인증번호가 없거나 시간 초과시 false 반환
        if (storedCode == null || storedTime == null) {
            return false;
        }


        // 시간이 5분을 초과하면 인증번호 만료 처리
        if (Duration.between(storedTime, LocalDateTime.now()).toMinutes() > TIMEOUT_MINUTES) {
            // 세션에서 인증번호와 발급 시간 삭제
            session.removeAttribute("verificationCode");
            session.removeAttribute("verificationTime");
            return false;
        }
        /*
        log.info("인증번호 검증 화면 Code={}",storedCode);
        log.info("인증번호 검증 화면 Time={}",storedTime);
        log.info("인증번호 검증 화면 유효시간 확인={}",Duration.between(storedTime, LocalDateTime.now()).toMinutes() > TIMEOUT_MINUTES);
        log.info("인증번호 검증 화면 유효시간 로직 값={}",Duration.between(storedTime, LocalDateTime.now()).toMinutes());
        log.info("인증번호 검증 화면 유효시간={}분", TIMEOUT_MINUTES);
        */

        // 인증번호 일치 여부 확인
        return storedCode.equals(userInputCode);
    }
}
