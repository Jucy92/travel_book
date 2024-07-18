package travel_book.service.web.mail.model;

import lombok.Data;

@Data
public class MailModel {
    private String receiver;
    private String title;
    private String content;
}
