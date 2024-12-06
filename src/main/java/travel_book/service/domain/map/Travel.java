package travel_book.service.domain.map;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.intellij.lang.annotations.Identifier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "TRAVEL")
public class Travel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // PK, DB에서 자동증가 설정 알려주기
    @Setter(AccessLevel.NONE)
    private Long travelId;
    @Column(length = 100)   // varchar(100)
    private String title;

    @Setter(AccessLevel.NONE)
    private Long oid;           // long 타입으로 선언 했는데, 이러면 쿼리 나가고 쿼리 값을 엔티티에 넣으려고 할 때 long = null을 넣으려고 하면서 오류 발생 -> long -> Long 래퍼 타입 변경
    private Long cid;
    private LocalDateTime cdt;

    /**
     * OneToMany = 1:N 관계 설정
     * mappedBy = "travel"->List<Location> (PK-FK) 관계 설정 - Location 엔티티에서 참조하는 travel 엔티티 설정(필드x 필드는 엔티티 통해서 접근)
     *   ㄴ> travelId 참조하는 Location 엔티티의 조인 컬럼명이랑 맞춰줘야 찾는다 / 어노테이션 컬럼명은 실제 테이블 컬럼명과 맞춰줘야함
     * cascade = CascadeType.ALL: - 모든 영속성 작업(저장,업데이트,삭제 등)에 대해서 Travel 엔티티에 따라 Location에 영향이 간다
     * orphanRemoval = true - Travel 엔티티에서 Location이 제거되면 Location 엔티티에서도 삭제된다.
     */
    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Location> locations = new ArrayList<>();

}
