package travel_book.service.web.profile;

import travel_book.service.web.profile.dto.ProfileData;

import java.util.List;

public interface ProfileService {
    ProfileData getProfile(String userId);
    List<String> getPosts(String userId);
}
