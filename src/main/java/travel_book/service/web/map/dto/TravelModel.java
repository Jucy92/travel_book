package travel_book.service.web.map.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class TravelModel {
    @Setter(AccessLevel.NONE)
    private Long travelId;
    private String title;
    private Long oid;           // long 타입으로 선언 했는데, 이러면 쿼리 나가고 쿼리 값을 엔티티에 넣으려고 할 때 long = null을 넣으려고 하면서 오류 발생 -> long -> Long 래퍼 타입 변경
    private Long cid;
    private LocalDateTime cdt;
}
