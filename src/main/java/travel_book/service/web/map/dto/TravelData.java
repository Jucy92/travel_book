package travel_book.service.web.map.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
//@Alias("TravelData")  // 여기서 이렇게 설정하면 xml 파일에서 resultType에 클래스명 바로 입력해도 인식함
public class TravelData {

    // TRAVEL 테이블 정보
    private Long travelId;          // 이거 여기서 사용안하고 DB에서 자동증가 해서 선언 안했는데 매퍼쪽에서 keyProperty="id" 오류 발생해서 추가 -> 자동 생성된 값을 여기로 저장해주기 때문에 필요
    private Long cid;               // id로 하면 오류는 안터지지만 id 값 안들어감
    private String title;           // 여행 제목

    // LOCATION 테이블 정보
    private Long locationId;        // 지역 번호 (마커 찍히는 순서번호) travelId(1024) - locationId(1,2,3,4...) 순차대로 증가해야함
    private double latitude;        // 위도
    private double longitude;       // 경도

    // LOCATION_DETAIL 테이블 정보
    private Long locationSq;        // 지역 번호 (마커 찍히는 순서번호) travelId(1024) - locationId(1,2,3,4...) 순차대로 증가해야함
    private String content;         // travelSq 증가 할 때 마다 하나씩 증가해줘야 할 거 같은데..
}
