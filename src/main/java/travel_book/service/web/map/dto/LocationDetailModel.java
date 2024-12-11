package travel_book.service.web.map.dto;

import lombok.Data;

@Data
public class LocationDetailModel {
    private Long travelId;
    private Long locationId;
    private Long locationSq;
    private String content;
}
