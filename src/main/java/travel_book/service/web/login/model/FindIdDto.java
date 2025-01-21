package travel_book.service.web.login.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class FindIdDto {


    @Email(message = "올바른 이메일 형식이 아닙니다.")   // 이건 이제 브라우저에서 지원해주는듯..?
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String mail;
    private String userId;
    private String name;
    private LocalDateTime cdt;
}
