package travel_book.service.web.map.dto;

import lombok.Data;

@Data
public class LocationModel {
    
    private long id;        // 이거 여기서 사용안하고 DB에서 자동증가 해서 선언 안했는데 매퍼쪽에서 keyProperty="id" 오류 발생해서 추가
    private Long memberId;
    private Long sq;        //sq로 안바꿔줘도 되겠지..? Mapper에서 해보고 제대로 안되면 sq로 변경해야함
    private double lat;
    private double lng;
    private String name;


    
    /*
    // Getters and Setters  -> Lombok @Data 대체

    public Long getId() {
        return sq;
    }

    public void setId(Long id) {
        this.sq = sq;
    }
    */
}
