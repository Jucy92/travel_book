package travel_book.service.web.map.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import travel_book.service.web.map.dto.*;


import java.util.List;
import java.util.Optional;

@Mapper         // 마이바티스 매핑 XML을 호출해주는 매퍼 인터페이스
public interface MapMapper {

    void saveTravel(TravelModel Travel);  // Member.save는 Member 반환인데 여기는 저장만하고 되돌려줄 필요 없겠지?
    void saveLocation(LocationModel location);  // Member.save는 Member 반환인데 여기는 저장만하고 되돌려줄 필요 없겠지?
    void saveLocationDetail(LocationDetailModel locationDetail);  // Member.save는 Member 반환인데 여기는 저장만하고 되돌려줄 필요 없겠지?
    TravelModel findById(Long travelId);
    List<LocationModel> findByLocation(Long travelId);
    List<LocationDetailModel> findByLocationDetail(@Param("travelId") Long travelId, @Param("locationId") Long locationId);
    List<TravelList> findByTravel(long id);
    List<TravelDetail> findByTravelId(@Param("travelId")long travelId);     // 파라미터가 2개 이상이면 @Param 명시해야 한다. 1개면 생략 가능
    List<TravelInformation> findTravelInfoByTravelId(Long travelId);

}
