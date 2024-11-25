package travel_book.service.web.map.dto;

import lombok.Data;

@Data
public class LocationModel {    // 모델을 두개로 나눠야하나..? 여행 계획 모델 / 상세 계획 모델

    //LOCATION_STORAGE_M 테이블 데이터
    private Long travelId;        // 이거 여기서 사용안하고 DB에서 자동증가 해서 선언 안했는데 매퍼쪽에서 keyProperty="id" 오류 발생해서 추가 -> 자동 생성된 값을 여기로 저장해주기 때문에 필요
    private String userId;          // 사용자 아이디
    private String title;           // 여행 제목 (나중에 불러오기 용도) 저장은 여행 계획 다 작성 후 저장할 때 팝업으로 뜨거나 다른 방법 생각
    private String tripStart;       // 여행 시작일 ex)2024-09-05
    private String tripEnd;         // 여행 마지막 ex)2024-09-18
    private String addressName;     // 지도에 찍히는 명칭 -> 근데 이건 받을 필요가 없을거 같긴한데..
    private String locationName;    // 장소명(or 건물명)
    private String rmksM;           // 마스터 비고

    //LOCATION_STORAGE_D (계획 데이터) + LOCATION (좌표 데이터)
    private Long travelSq;          // 여행지 순서(클릭 좌표)
    private double latitude;             // 위도

    private double longitude;             // 경도
    private String hour00;          // 00:00 ~ 00:59
    private Integer dayTrip;        // 여행 일자(2박3일 중 1일차)
    private String rmksD;           // 디테일 비고

    private String hour01;
    private String hour02;
    private String hour03;
    private String hour04;
    private String hour05;
    private String hour06;
    private String hour07;
    private String hour08;
    private String hour09;
    private String hour10;
    private String hour11;
    private String hour12;          // 12:00 ~ 12:59 일정
    private String hour13;
    private String hour14;
    private String hour15;
    private String hour16;
    private String hour17;
    private String hour18;
    private String hour19;
    private String hour20;
    private String hour21;
    private String hour22;
    private String hour23;

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
