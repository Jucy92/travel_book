package travel_book.service.web.map.dto;

import lombok.Data;

@Data
public class LocationSearchCond {
    private long memberId;      // 이 값을 받아서 member userId, userName 받아야함
    private String userId;
    private String userName;
}
