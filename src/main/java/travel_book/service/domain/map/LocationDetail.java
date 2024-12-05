package travel_book.service.domain.map;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
public class LocationDetail {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)   // AUTO_INCREMENT 옵션 뺌
    //@Setter(AccessLevel.NONE)
    @Column(name = "LOCATION_SQ")
    private Long locationSq;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_ID")
    private Travel travel;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @Column(name = "CONTENT")
    private String content;
}
