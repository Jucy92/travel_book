package travel_book.service.domain.map;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
public class LocationDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "LOCATION_SQ")
    private long locationSq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_ID")
    private long travelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    private long locationId;  // 위에서 명시 해줘서 여기선 이렇게 쓰나보구나

    @Column(name = "CONTENT")
    private String content;
}
