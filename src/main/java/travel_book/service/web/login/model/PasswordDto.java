package travel_book.service.web.login.model;

import lombok.Data;

@Data
public class PasswordDto {
    private String password;
    private String confirmPassword;
}
