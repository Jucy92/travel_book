package travel_book.service.web.login.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginModel {

//    @Email
//    @NotBlank(message = "메일주소를 입력해 주세요.")
//    private String mail;
    @NotBlank(message = "아이디를 입력해 주세요.")
    private String userId;
    @NotEmpty(message = "비밀번호를 입력해 주세요.")
    private String password;
    //private String phone;     // 로그인 페이지에서는 두개만 입력 받으면 되는거니깐..?
}
