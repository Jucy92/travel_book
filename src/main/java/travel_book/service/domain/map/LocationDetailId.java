package travel_book.service.domain.map;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class LocationDetailId implements Serializable {
    @Column(name = "TRAVEL_ID")
    private Long travelId;
    @Column(name = "LOCATION_ID")
    private Long locationId;
    @Column(name = "LOCATION_SQ")
    private Long locationSq;
}
