package travel_book.service.domain.map;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "LOCATION_DETAIL")
public class LocationDetail {
    @EmbeddedId
    private LocationDetailId id;

    /*
    @MapsId("travelId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_ID")
    private Travel travel;
     */
    //@MapsId("travelId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "TRAVEL_ID", /*referencedColumnName = "TRAVEL_ID",*/ insertable=false, updatable=false),
            @JoinColumn(name = "LOCATION_ID", /*referencedColumnName = "LOCATION_ID",*/ insertable=false, updatable=false)
    })
    private Location location;

    @Column(name = "CONTENT")
    private String content;
}
