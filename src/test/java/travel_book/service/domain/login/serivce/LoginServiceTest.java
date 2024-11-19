package travel_book.service.domain.login.serivce;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional        // test 메서드나 클래스에 있는 경우 성공 유무와 상관 없이 자동으로 롤백 한다.
@SpringBootTest        // @SpringBootTest를 선언하면 @SpringBootApplication를 찾아가서 거기에 설정되어 있는 설정을 그대로를 가져다가 테스트를 실행한다
class LoginServiceTest {

    @Test
    @DisplayName("로그인 테스트")
    void login() {

    }
}