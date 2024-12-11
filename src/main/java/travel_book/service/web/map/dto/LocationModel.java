package travel_book.service.web.map.dto;

import lombok.Data;
import travel_book.service.domain.map.Travel;

@Data
public class LocationModel {    // 모델을 두개로 나눠야하나..? 여행 계획 모델 / 상세 계획 모델

    private Long travelId;
    private Long locationId;
    private Double latitude;
    private Double longitude;
}
