package travel_book.service.web.profile;

import travel_book.service.web.profile.dto.ProfileData;

import java.util.List;

public interface ProfileService {
    ProfileData getProfile(String userId);
    List<?> getPosts(String userId);
    List<?> getTravelList(String userId);
    List<?> getTravelInformation(Long travelId);
}
