package travel_book.service.web.login.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class FindPasswordDto extends FindIdDto{

    @NotBlank
    private String userId;
    // @Data 롬북을 사용하니깐 상속받아진 getName, setName도 호출이 되네... 흠..

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "FindPasswordDto{" +
                "mail='" + super.getMail() + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
