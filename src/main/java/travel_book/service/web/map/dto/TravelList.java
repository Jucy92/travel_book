package travel_book.service.web.map.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
//@Alias("TravelList")  // 여기서 이렇게 설정하면 xml 파일에서 resultType에 클래스명 바로 입력해도 인식함
public class TravelList {
    private Long travelId;      // 여행 번호 -> 화면에서는 숨김 처리 해뒀다가 행 클릭하면 디테일 정보 가져오는 용
    private String title;       // 여행 제목
    private String userId;      // 사용자 ID
    private LocalDateTime cdt;  // 작성 일자
}
