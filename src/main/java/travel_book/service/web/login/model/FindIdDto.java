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

   /* public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCdt() {
        return cdt;
    }

    public void setCdt(LocalDateTime cdt) {
        this.cdt = cdt;
    }

    @Override
    public String toString() {
        return "FindIdDto{" +
                "mail='" + mail + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", cdt=" + cdt +
                '}';
    }*/
}
