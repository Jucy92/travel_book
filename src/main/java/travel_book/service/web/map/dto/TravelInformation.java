package travel_book.service.web.map.dto;

import lombok.Data;

@Data
public class TravelInformation { // 프로필에서 여행 리스트 선택 했을 때 사용되는 모델
    private Long travelId;
    private String title;
    private Long locationId;
    private Long locationSq;
    private String content;
    private String locationUploadImage;
}
