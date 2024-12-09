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
    //@MapsId("locationId")   // travelId, locationId 둘다 중복이라고 막 떠서 그냥 id했는데.. 얘는 Location의 id일까 Detail의 id일까..
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "TRAVEL_ID", referencedColumnName = "TRAVEL_ID", insertable=false, updatable=false),
            @JoinColumn(name = "LOCATION_ID", referencedColumnName = "LOCATION_ID", insertable=false, updatable=false)
    })
    private Location location;

    @Column(name = "CONTENT")
    private String content;
}
