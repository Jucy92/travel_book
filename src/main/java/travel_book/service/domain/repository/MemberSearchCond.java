package travel_book.service.domain.repository;


import lombok.Data;

@Data
public class MemberSearchCond {

    private String mail;
    private String userId;
    private String name;

    public MemberSearchCond() {
    }

    public MemberSearchCond(String userId) {
        this.userId = userId;
    }



    public MemberSearchCond(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }
    public MemberSearchCond(String mail, String userId, String name) {
        this.mail = mail;
        this.userId = userId;
        this.name = name;
    }
}
