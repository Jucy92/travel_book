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
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // AUTO_INCREMENT 옵션 뺌
    @Column(name = "LOCATION_ID")
    //@Setter(AccessLevel.NONE) // AUTO_INCREMENT 빼서 값 넣어줘야함
    private Long locationId;

    /**
     * ManyToOne = N:1 관계 설정 -> 받는 쪽(FK)
     * fetch = FetchType.LAZY - 지연 로딩 -> 성능 최적화 -> Location 조회해도 Travel은 실제로 사용할 때 까지 로딩 x
     * @JoinColumn(name = "TRAVEL_ID") - 외래키 설정
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_ID")   // 조인 컬럼에 대해서는 타입이 아니라 엔티티로 받아서 컬럼명 반드시 명시적으로 표시
    private Travel travel;

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