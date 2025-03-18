package travel_book.service.web.profile.dto;

import lombok.Data;

@Data
public class ProfileData {

    //private Long id;  // 필요할까..?
    private String userId;
    private String name;
    private String profileImage; // 이미지는 어떻게 받아야해..?
    private String postsCount;
    private String followersCount;
    private String followingCount;
}
