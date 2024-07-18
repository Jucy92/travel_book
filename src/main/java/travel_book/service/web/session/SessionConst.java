package travel_book.service.web.session;

public class SessionConst {
    public static final String SESSION_NAME = "loginSession";
}

/**
 * public abstract class SessionConst {         이렇게 추상화 클래스로 만들어서 객체로 생성(new) 못하게 막거나
 *     public static final String LOGIN_MEMBER = "loginMember";
 * }
 *
 * public interface SessionConst {              인터페이스로 해서 사용하는게 좋다 -> 실수 방지
 *     static String LOGIN_MEMBER = "loginMember";
 *     //public static String LOGIN_MEMBER = "loginMember";    // 인터페이스는 그냥 디폴트가 public -> 무조건 가져다 써야해서 그런가봄
 * }

 */
