package travel_book.service.domain.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {

//    @NotNull  여기서 체크해서..
    private Long id;
    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일을 올바르게 입력해주세요")    // 도메인별 체크도 확인해서 추가
    private String mail;        // 중복 방지를 위해 인증용
    @NotBlank
    private String name;        // 사용자 이름
    @NotBlank
    private String userId;      // 사용자 ID
    @NotBlank
    private String password;
    private String phone;
    private String crtfNum;
    private LocalDateTime cdt;

}
