package travel_book.service.domain.map;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class LocationId implements Serializable {
    @Column(name = "TRAVEL_ID")     // 얘는 TRAVEL_ID로 정상적으로 찍히는데
    private Long travelId;
    @Column(name = "LOCATION_ID")   // 얘는 왜 location_id로 찍히냐
    private Long locationId;
}
