package travel_book.service.domain.location;

public class LocationStorage {
    private Long id;             // 자동 생성 ID값
    private Long memberId;       // 사용자 ID값

    private double latitude;    // 위도
    private double longitude;    // 경도
    private String name;        // 장소명
    private Long sq;             // 체크 순서

}
