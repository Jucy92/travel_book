package travel_book.service.domain.repository;


import lombok.Data;

@Data
public class MemberSearchCond {

    private String mail;
    private String userId;
    private String name;

    /*
    // 생성자가 필요해?? @Data 통해서 변경이 가능한데..?
    public MemberSearchCond() {
    }


    public MemberSearchCond(String userId) {
        this.userId = userId;
    }

    public MemberSearchCond(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }
    public MemberSearchCond(String mail, String userId) {
        this.mail = mail;
        this.userId = userId;
    }
    public MemberSearchCond(String mail, String userId, String name) {
        this.mail = mail;
        this.userId = userId;
        this.name = name;
    }
    */

}
