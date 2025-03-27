package travel_book.service.web.map.dto;

import lombok.Data;

@Data
//@Alias("TravelDetail")  // 여기서 이렇게 설정하면 xml 파일에서 resultType에 클래스명 바로 입력해도 인식함
public class TravelBasicData {

    // TRAVEL 테이블 정보
    private Long travelId;          // 이거 여기서 사용안하고 DB에서 자동증가 해서 선언 안했는데 매퍼쪽에서 keyProperty="id" 오류 발생해서 추가 -> 자동 생성된 값을 여기로 저장해주기 때문에 필요

    // LOCATION 테이블 정보
    private Long locationId;        // 장소 번호
    private double latitude;        // 위도
    private double longitude;       // 경도

    // LOCATION_DETAIL 테이블 정보
    private Long locationSq;        // 장소 디테일 번호
    private String content;         // 디테일 내용
}
