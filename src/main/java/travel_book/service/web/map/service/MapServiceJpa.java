package travel_book.service.web.map.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travel_book.service.domain.map.*;
import travel_book.service.web.map.dao.LocationDetailRepository;
import travel_book.service.web.map.dao.LocationRepository;
import travel_book.service.web.map.dao.TravelRepository;
import travel_book.service.web.map.dto.TravelData;

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
        log.info("savedTravel={}", savedTravel);

        List<Map<String, Object>> locations = (List<Map<String, Object>>) data.get("locations");
        List<Map<String, Object>> locationDetails = (List<Map<String, Object>>) data.get("locationDetails");

        // Location 신규 데이터 추가
        for (Map<String, Object> location : locations) {
            Location newLocation = new Location();
            LocationId locationId = new LocationId();
            locationId.setTravelId(savedTravel.getTravelId());
            locationId.setLocationId(((Integer) location.get("locationId")).longValue());   // 정수를 Object 타입으로 받으면 Java는 기본적으로 Integer 타입으로 인식하고, Integer 를 Long으로 형변환 하면 캐스트예외발생한다

            newLocation.setId(locationId);          // LocationId 통해서 다중 PK 값 저장
            //newLocation.setTravel(savedTravel);     // FK라서 저장 했던 건가...? 지금은 복합 PK로 해서 의미없나..? -> 지워도 될 거 같은데 엔티티에서 설정 해주고 여기서 값 넘겨주면 JPA에서 FK 무결성 보장해주는 듯
            newLocation.setLatitude((Double) location.get("lat"));
            newLocation.setLongitude((Double) location.get("lng"));
            Location savedLocation = locationRepository.save(newLocation);
            log.info("savedLocation={}", savedLocation);

            // LocationDetail 신규 데이터 추가
            for (Map<String, Object> locationDetail : locationDetails) {
                LocationDetail newLocationDetail = new LocationDetail();    // 스프링 빈 등록해서 싱글톤으로 쓰자니 동시에 데이터 접근 시 값 이슈 생길거라 못함
                LocationDetailId locationDetailId = new LocationDetailId(); // 그치만, 매번 쿼리 던질 때마다 DB에 붙는 것보다 인스턴스 생겼다가 GC 처리되는게 효율적 일듯
                locationDetailId.setTravelId(savedTravel.getTravelId());
                locationDetailId.setLocationId(savedLocation.getId().getLocationId());
                locationDetailId.setLocationSq(Long.valueOf((Integer) locationDetail.get("locationSq")));

                newLocationDetail.setId(locationDetailId);
                //newLocationDetail.setTravel(savedTravel);
                newLocationDetail.setLocation(savedLocation);

                newLocationDetail.setContent((String) locationDetail.get("content"));
                LocationDetail savedDetail = locationDetailRepository.save(newLocationDetail);
                log.info("savedDetail={}", savedDetail);

            }

        }


    }

    public Travel copyOfAllItinerary(long travelId, long copyUserId) {   // 여행 정보 전체 복사 (파라미터: 여행 번호, 복사하는 사용자 ID)
        Travel originalTravel = travelRepository.findById(travelId).orElseThrow(() -> new EntityNotFoundException("Travel not found")); // 원본 데이터 조회

        Travel newTravel = new Travel();    // 객체 생성 = 새로운 INSERT !!! ORM 장점!!!!!!!!!!  -> 실제 insert는 아래 save 되는 시점
        newTravel.setTitle(originalTravel.getTitle());
        newTravel.setCid(copyUserId);
        newTravel.setCdt(LocalDateTime.now());

        Travel saveTravel = travelRepository.save(newTravel);       // 저장되는 시점에 travelId 리턴

        List<Location> originalLocations = originalTravel.getLocations();   // 원본 데이터의 키(travelId) 값으로 Location 테이블의 FK를 찾고, 그 정보를 다 가져온다

        for (Location originalLocation : originalLocations) {

            Location newLocation = new Location();
            newLocation.setTravel(saveTravel);              // 이 값을 안넣어주면 영속성 작업에 이슈가 생김 -> detail에서 값을 못받음
            newLocation.setId(originalLocation.getId());    // JPA에서 DB-Entity 연결해서 데이터 가져올 때 담아줬곘지..? -> 담아줌 Id 호출 시점에 travel_id로 조회해서 값 2개 담아줌
            newLocation.setLatitude(originalLocation.getLatitude());
            newLocation.setLongitude(originalLocation.getLongitude());

            Location saveLocation = locationRepository.save(newLocation);
            log.info("location={}", saveLocation);
            log.info("locationId={}", saveLocation.getId());

            List<LocationDetail> originalLocationDetails = originalLocation.getLocationDetails();
            log.info("Detail 데이터 유/무={}", originalLocationDetails);
            for (LocationDetail originalLocationDetail : originalLocationDetails) {
                log.info("Detail 시작");  // -> ★★★ 여긴 들어오지도 않았고, 트랜잭션 끝나면서 커밋되면서 삭제됨 ★★★ 여기부터 다시 분석 포스트맨

                LocationDetail newDetail = new LocationDetail();
                //newDetail.setTravel(saveTravel);    // saveLocation.getTravelId() 가져가도 상관은 없을거 같은데 메인 테이블 정보를 가져가자
                newDetail.setLocation(saveLocation);  // save 하기 전 이미 org_location_id 가져오기 때문에 saveLocation이든, org.saveLocation든 상관 없음
                newDetail.setId(originalLocationDetail.getId());
                newDetail.setContent(originalLocationDetail.getContent());

                LocationDetail saveDetail = locationDetailRepository.save(newDetail);
                log.info("detail={}", saveDetail);
                log.info("detailId={}", saveDetail.getId());
            }

            log.info("location 저장 완료!!!!!!!!!!!!!");

        }
        log.info("saveTravel={}", saveTravel);
        log.info("saveTravelId={}", saveTravel.getTravelId());

        return saveTravel;
    }
}
