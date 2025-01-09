package travel_book.service.web.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import travel_book.service.domain.member.Member;
import travel_book.service.domain.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "jcy92@gmail.com";
    private static int number;

    public static void createNumber() {
        number = (int) (Math.random() * (90000)) + 100000;  // (int) Math.random() * (최대값-최소값+1) + 최소값
    }

    public MimeMessage CreateMail(String mail){
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try{
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "이메일 인증 코드 보내드립니다." + "</h3>";
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
        MimeMessage message = CreateMail(mail);
        javaMailSender.send(message);
        return number;
    }

    public boolean checkedMail(String mail) {
        //Member member = memberRepository.findByMail(mail).orElse(null);
        boolean memberExists = memberRepository.findByMail(mail).isPresent();   // Optional 기능 (isPresent 값이 있으면 true 없으면 false / isEmpty 값이 없으면 true)
        return memberExists;
    }
}
