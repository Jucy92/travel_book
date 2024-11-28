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
public class Travel {
    //@Column(name = "travel_id")   // 아마.. 얘도 카멜표기법 지원 된다고 했던거 같은데
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // PK, DB에서 자동증가 설정 알려주기
    @Setter(AccessLevel.NONE)
    private long travelId;
    @Column(length = 100)   // varchar(100)
    private String title;

    @Setter(AccessLevel.NONE)
    private long oid;
    private long cid;
    private LocalDateTime cdt;

    /**
     * OneToMany = 1:N 관계 설정
     * mappedBy = "travelId"->List<Location> (PK-FK) 관계 설정 -  Location 엔티티에서 travelId 참조하는 필드명
     *              ㄴ> 저렇게 썼더니 JPA에서 테이블 연결할 때 entity 못찾으면서 에러 발생 어차피 entity안에 .getTravelId 불러 올 수 있으니 entity로 설정해야한다.
     *                  굳이굳이 entity말고 PK로 직접 FK 설정해주면, 연결은 되지만 영속성 연결 작업을 직접 작성해줘야한다. -> *** 한마디로 쓰지마라 ***
     * cascade = CascadeType.ALL: - 모든 영속성 작업(저장,업데이트,삭제 등)에 대해서 Travel 엔티티에 따라 Location에 영향이 간다
     * orphanRemoval = true - Travel 엔티티에서 Location이 제거되면 Location 엔티티에서도 삭제된다.
     */
    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Location> locations = new ArrayList<>();

}
