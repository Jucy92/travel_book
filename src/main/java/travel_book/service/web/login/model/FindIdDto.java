package travel_book.service.web.login.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;


public class FindIdDto {


    @Email
    @NotBlank(message = "메일주소를 입력해 주세요.")
    private String mail;
    private String userId;
    private String name;
    private LocalDateTime cdt;

    public String getMail() {
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
    }
}
