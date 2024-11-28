package travel_book.service.domain.map;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "LOCATION")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCATION_ID")
    @Setter(AccessLevel.NONE)
    private Long locationId;

    /**
     * ManyToOne = N:1 관계 설정 -> 받는 쪽(FK)
     * fetch = FetchType.LAZY - 지연 로딩 -> 성능 최적화 -> Location 조회해도 Travel은 실제로 사용할 때 까지 로딩 x
     * @JoinColumn(name = "TRAVEL_ID") - 외래키 설정
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_ID")
    private Travel travel;    // 필드명 travel 으로 쓰려면 위 매핑 해야하고(테이블-엔티티 매칭), 얘는 FK 필드 값을 바로 쓰는게 아니라 부모테이블 Entity 연결용,
                                // 필드를 쓰려면 엔티티.getTravelId 해서 써야하나..?

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    /**
     *  OneToMany = 1:N 관계 설정 -> 주는 쪽(PK)
     *  mappedBy = "location"->List<LocationDetail> (PK-FK)관계 설정 -  LocationDetail 엔티티에서 참조하는 location 엔티티 설정(필드x 필드는 엔티티 통해서 접근)
     *  cascade = CascadeType.ALL: - 모든 영속성 작업(저장,업데이트,삭제 등)에 대해서 location 엔티티에 따라 LocationDetail 영향이 간다
     *  orphanRemoval = true - location 엔티티에서 LocationDetail 제거되면 LocationDetail 엔티티에서도 삭제된다.
     */
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LocationDetail> locationDetails = new ArrayList<>();

}