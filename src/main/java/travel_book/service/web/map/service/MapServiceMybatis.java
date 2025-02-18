package travel_book.service.web.map.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travel_book.service.domain.repository.mybatis.MyBatisRepository;
import travel_book.service.web.map.MapService;
import travel_book.service.web.map.dao.MapMapper;
import travel_book.service.web.map.dto.*;


import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
@Service
public class MapServiceMybatis implements MapService {

    /*      // 카카오맵 도보 경로 받아오기 위해 선언했던 내용
    @Value("${appkey=31392fb12fa81362020946d15afde9c2}")
    private String kakaoApiKey;
    */

    private final MapMapper mapMapper;

    public MapServiceMybatis(MapMapper mapMapper) {
        this.mapMapper = mapMapper;
    }

    public TravelModel saveTravel(TravelModel travel) {     // Travel 저장(INSERT)
        mapMapper.saveTravel(travel);                       // void로 반환 받아도 travelId 값 저장되는듯, int 타입으로 받으면 실행 행수 리턴해줌
        return travel;                                      // 저장 후 travel 리턴하면 저장된 값이 들어가있음(AUTO_INCREMENT 되는  travelId 값 포함)
    }
    public LocationModel saveLocation(LocationModel location) {
        mapMapper.saveLocation(location);
        return location;
    }
    public LocationDetailModel saveLocationDetail(LocationDetailModel locationDetail) {
        mapMapper.saveLocationDetail(locationDetail);
        return locationDetail;
    }

    public TravelModel findById(Long travelId) {            // Travel 조회(SELECT)
        return mapMapper.findById(travelId);
    }

    public List<LocationModel> findByLocation(Long travelId) {
        return mapMapper.findByLocation(travelId);
    }

    public List<LocationDetailModel> findByLocationDetail(Long travelId, Long locationId) {
        return mapMapper.findByLocationDetail(travelId, locationId);
    }

    public List<TravelList> findByTravel(long id) {     // 사용자 ID로 저장된 여행 리스트 가져오기
        return mapMapper.findByTravel(id);
    }

    public List<TravelDetail> findByTravelId(long travelId) {    // travelId로 여행 한 건에 대한 모든 정보 가져오기
        return mapMapper.findByTravelId(travelId);
    }

    public void addItinerary(Map<String, Object> data, long id) {
        log.info("RequestBody={}", data);

        //Map<String, Object> travel = (Map<String, Object>) data.get("travel");
        List<Map<String, Object>> locations = (List<Map<String, Object>>) data.get("locations");
        List<Map<String, Object>> locationDetails = (List<Map<String, Object>>) data.get("locationDetails");

        //log.info("travel", travel);
        log.info("locations", locations);
        log.info("locationDetails", locationDetails);

        TravelModel newTravel = new TravelModel();
        //newTravel.setTitle((String)travel.get("title"));
        newTravel.setTitle((String) data.get("title"));
        newTravel.setOid(id);
        newTravel.setCid(id);

        log.info("newTravel={}", newTravel);
        TravelModel savedTravel = saveTravel(newTravel);
        log.info("savedTravel={}", savedTravel);
        System.out.println(getClass().getSimpleName() + " travelId = " + savedTravel.getTravelId());

        for (Map<String, Object> location : locations) {
            LocationModel newLocation = new LocationModel();
            newLocation.setTravelId(savedTravel.getTravelId());
            newLocation.setLocationId(Long.valueOf((Integer) location.get("locationId")));
            newLocation.setLatitude((Double) location.get("lat"));
            newLocation.setLongitude((Double)location.get("lng"));

            log.info("newLocation={}", newLocation);
            LocationModel savedLocation = saveLocation(newLocation);
            log.info("savedLocation={}", savedLocation);


            for (Map<String, Object> locationDetail : locationDetails) {
                //
                if (savedLocation.getLocationId() != Long.valueOf((Integer) locationDetail.get("locationId"))) {
                    continue;   // 처리 안해줬더니 id=1일 때 디테일에서 id=2인 데이터 넣으려다가 FK 오류 발생 / JPA는 엔티티정보를 가지고 알아서 넣어준건가..?
                }
                LocationDetailModel newLocationDetail = new LocationDetailModel();
                newLocationDetail.setTravelId(savedTravel.getTravelId());
                newLocationDetail.setLocationId(Long.valueOf((Integer) locationDetail.get("locationId")));
                newLocationDetail.setLocationSq(Long.valueOf((Integer) locationDetail.get("locationSq")));
                newLocationDetail.setContent((String) locationDetail.get("content"));

                log.info("newLocationDetail={}", newLocationDetail);
                LocationDetailModel savedLocationDetail = saveLocationDetail(newLocationDetail);
                log.info("savedLocationDetail={}", savedLocationDetail);
            }
        }
        //mapMapper.saveLocation(location);
    }


    public void copyOfAllItinerary(Long travelId, Long userId, String title) {
        TravelModel originalTravel = findById(travelId);
        Long originalTravelId = originalTravel.getTravelId();   // 원본 travelId -> 기존 데이터 조회용
        TravelModel newTravel = new TravelModel();
        newTravel.setOid(originalTravel.getOid());
        newTravel.setCid(userId);
        newTravel.setTitle(title);  // 복사하면서 넘겨받은 제목 저장

        TravelModel savedTravel = saveTravel(newTravel);
        log.info("savedTravel={}",savedTravel);
        Long savedTravelId = savedTravel.getTravelId();           // 복사본 travelId -> 신규 데이터 저장용

        List<LocationModel> originalLocations = findByLocation(originalTravelId);   // LocationModel 빈 껍데기에 setTravelId 해서 저장해놓고 넘기면? 해당 번호로 조회해서 나온 값을 담아주나?
        for (LocationModel originalLocation : originalLocations) {
            LocationModel newLocation = new LocationModel();
            newLocation.setTravelId(savedTravelId);
            newLocation.setLocationId(originalLocation.getLocationId());
            newLocation.setLatitude(originalLocation.getLatitude());
            newLocation.setLongitude(originalLocation.getLongitude());

            LocationModel savedLocation = saveLocation(newLocation);
            log.info("savedLocation={}",savedLocation);

            List<LocationDetailModel> originalLocationDetails = findByLocationDetail(originalTravelId, originalLocation.getLocationId());   // locationId 같은 것만 나옴
            for (LocationDetailModel originalLocationDetail : originalLocationDetails) {
                // Location.locationId == Detail.locationId 체크 필요 x
                log.info("originalLocationDetail={}", originalLocationDetail );
                LocationDetailModel newLocationDetail = new LocationDetailModel();
                newLocationDetail.setTravelId(savedTravelId);
                newLocationDetail.setLocationId(originalLocationDetail.getLocationId());
                newLocationDetail.setLocationSq(originalLocationDetail.getLocationSq());
                newLocationDetail.setContent(originalLocationDetail.getContent());
                LocationDetailModel savedLocationDetail = saveLocationDetail(newLocationDetail);
                log.info("savedLocationDetail={}",savedLocationDetail);
            }
        }
    }
}
