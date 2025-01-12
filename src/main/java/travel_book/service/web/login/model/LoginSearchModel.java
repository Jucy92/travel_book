package travel_book.service.web.login.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginSearchModel {


    @Email
    @NotBlank(message = "메일주소를 입력해 주세요.")
    private String mail;
    @NotBlank
    private String domain;
    @NotBlank(message = "아이디를 입력해 주세요.")
    private String userId;

}
