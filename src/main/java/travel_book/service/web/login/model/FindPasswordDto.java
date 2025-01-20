package travel_book.service.web.login.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FindPasswordDto extends FindIdDto{

    @NotBlank
    private String userId;
    private String mail;
    // @Data 롬북을 사용하니깐 상속받아진 getName, setName도 호출이 되네... 흠..

}
