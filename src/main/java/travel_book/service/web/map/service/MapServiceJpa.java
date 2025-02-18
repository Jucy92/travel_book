package travel_book.service.web.map.service;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travel_book.service.domain.map.Location;
import travel_book.service.domain.map.LocationDetail;
import travel_book.service.domain.map.Travel;
import travel_book.service.web.map.dao.LocationDetailRepository;
import travel_book.service.web.map.dao.LocationRepository;
import travel_book.service.web.map.dao.TravelRepository;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class MapServiceJpa {
    private final TravelRepository travelRepository;
    private final LocationRepository locationRepository;
    private final LocationDetailRepository locationDetailRepository;

    //public void addItinerary(TravelData data, long id) {
    public void addItinerary(Map<String, Object> data, long id) {
        // Map<Str,obj> => obj 안에 단일 데이터, 리스트가 있음 => 리스트는 Map<Str1,Str2> 형식으로 들어 있음

        // Travel 신규 데이터 추가
        Travel travel = new Travel();
        
        try {   // @data Setter Access.none 하고 사용하려고 이짓을 하고있네.. 복습 개념으로 해두자 -> travel.setOid 역할 
            Field oid = null;
            oid = travel.getClass().getDeclaredField("oid");
            oid.setAccessible(true);
            oid.set(travel, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("class:" + getClass()
                    + " simpleName:" + getClass().getSimpleName()
                    + " addItinerary 메서드: 여행 계획 넣는 화면 예외 발생" + e);
        }

        travel.setTitle((String) data.get("title"));
        travel.setCid(id);
        travel.setCdt(LocalDateTime.now());

        Travel savedTravel = travelRepository.save(travel);     // TRAVEL INSERT - travelId 값 저장 받음
        log.info("savedTravel={}",savedTravel);

        List<Map<String, Object>> locations = (List<Map<String, Object>>) data.get("locations");
        List<Map<String, Object>> locationDetails = (List<Map<String, Object>>) data.get("locationDetails");

        // Location 신규 데이터 추가
        for (Map<String, Object> location : locations) {
            Location newLocation = new Location();
            newLocation.setTravel(savedTravel);
            newLocation.setLocationId(((Integer) location.get("locationId")).longValue());  // 정수를 Object 타입으로 받으면 Java는 기본적으로 Integer 타입으로 인식하고, Integer 를 Long으로 형변환 하면 캐스트예외발생한다
            newLocation.setLatitude((Double) location.get("lat"));
            newLocation.setLongitude((Double) location.get("lng"));
            Location savedLocation = locationRepository.save(newLocation);
            log.info("savedLocation={}", savedLocation);

            // LocationDetail 신규 데이터 추가
            for (Map<String, Object> locationDetail : locationDetails) {
                LocationDetail newLocationDetail = new LocationDetail();
                newLocationDetail.setTravel(savedTravel);
                newLocationDetail.setLocation(savedLocation);
                newLocationDetail.setLocationSq(Long.valueOf((Integer) locationDetail.get("locationSq")));
                newLocationDetail.setContent((String) locationDetail.get("content"));
                LocationDetail savedDetail = locationDetailRepository.save(newLocationDetail);
                log.info("savedDetail={}", savedDetail);

            }

        }

        


    }

    public Travel copyOfAllItinerary(long travelId, long copyUserId) {   // 여행 정보 전체 복사 (파라미터: 여행 번호, 복사하는 사용자 ID)
        Travel originalTravel = travelRepository.findById(travelId).orElseThrow(() -> new EntityNotFoundException("Travel not found")); // 원본 데이터 조회

        Travel newTravel = new Travel();    // 객체 생성 = 새로운 INSERT !!! ORM 장점!!!!!!!!!!
        newTravel.setTitle(originalTravel.getTitle());
        newTravel.setOid(originalTravel.getOid());  // 엔티티에서 Setter 설정 뻄 -> 나중에 카운팅 하기 위해 / 전체복사는 가져가는데.. 일부 복사는 어쩌지? 누가 원본이지?
        newTravel.setCid(copyUserId);
        newTravel.setCdt(LocalDateTime.now());

        Travel saveTravel = travelRepository.save(newTravel);       // 저장되는 시점에 travelId 리턴

        List<Location> originalLocations = originalTravel.getLocations();   // 원본 데이터의 키(travelId) 값으로 Location 테이블의 FK를 찾고, 그 정보를 다 가져온다

        for (Location originalLocation : originalLocations) {

            Location newLocation = new Location();
            newLocation.setTravel(saveTravel);
            //newLocation.setLocationId(originalLocation.getLocationId());    // AUTO_INCREMENT 된 컬럼에도 직접 값을 넣을 수 있다 -> 입력된 최대 값에서 증가되기 시작 -> DB 지식
            newLocation.setLatitude(originalLocation.getLatitude());
            newLocation.setLongitude(originalLocation.getLongitude());

            Location saveLocation = locationRepository.save(newLocation);
            log.info("location={}",saveLocation);
            log.info("locationId={}",saveLocation.getLocationId());

            List<LocationDetail> originalLocationDetails = originalLocation.getLocationDetails();
            for (LocationDetail originalLocationDetail : originalLocationDetails) {

                LocationDetail newDetail = new LocationDetail();
                newDetail.setTravel(saveTravel);    // saveLocation.getTravelId() 가져가도 상관은 없을거 같은데 메인 테이블 정보를 가져가자
                newDetail.setLocation(saveLocation);  // save 하기 전 이미 org_location_id 가져오기 때문에 saveLocation이든, org.saveLocation든 상관 없음
                //newDetail.setLocationSq(originalLocationDetail.getLocationSq());
                newDetail.setContent(originalLocationDetail.getContent());

                locationDetailRepository.save(newDetail);
            }

        }
        log.info("saveTravel={}",saveTravel);
        log.info("saveTravelId={}",saveTravel.getTravelId());

        return saveTravel;
    }
}
