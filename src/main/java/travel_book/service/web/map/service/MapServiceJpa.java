package travel_book.service.web.map.service;

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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class MapServiceJpa {
    private final TravelRepository travelRepository;
    private final LocationRepository locationRepository;
    private final LocationDetailRepository locationDetailRepository;


    public Travel copyOfAllItinerary(long travelId, long copyUserId) {   // 여행 정보 전체 복사 (파라미터: 여행 번호, 복사하는 사용자 ID)
        Travel originalTravel = travelRepository.findById(travelId).orElseThrow(() -> new EntityNotFoundException("Travel not found")); // 원본 데이터 조회

        Travel newTravel = new Travel();    // 객체 생성 = 새로운 INSERT !!! ORM 장점!!!!!!!!!!
        newTravel.setTitle(originalTravel.getTitle());
        newTravel.setCid(copyUserId);
        newTravel.setCdt(LocalDateTime.now());

        Travel saveTravel = travelRepository.save(newTravel);       // 저장되는 시점에 travelId 리턴

        List<Location> originalLocations = originalTravel.getLocations();   // 원본 데이터의 키(travelId) 값으로 Location 테이블의 FK를 찾고, 그 정보를 다 가져온다

        for (Location originalLocation : originalLocations) {

            Location newLocation = new Location();
            newLocation.setTravel(saveTravel);  // 여기서 타입 오류 나서 확인 했더니 long이 아니라 Travel 클래스 타입이었네? 그게 가능한가
            newLocation.setLatitude(originalLocation.getLatitude());
            newLocation.setLongitude(originalLocation.getLongitude());

            Location saveLocation = locationRepository.save(newLocation);
            log.info("location={}",saveLocation);
            log.info("locationId={}",saveLocation.getLocationId());

            List<LocationDetail> originalLocationDetails = originalLocation.getLocationDetails();
            for (LocationDetail originalLocationDetail : originalLocationDetails) {

                LocationDetail newDetail = new LocationDetail();
                newDetail.setTravel(saveTravel);    // saveLocation.getTravelId() 가져가도 상관은 없을거 같은데 메인 테이블 정보를 가져가자
                newDetail.setLocation(saveLocation);
                newDetail.setContent(originalLocationDetail.getContent());

                locationDetailRepository.save(newDetail);
            }

        }
        log.info("saveTravel={}",saveTravel);
        log.info("saveTravelId={}",saveTravel.getTravelId());

        return saveTravel;
    }
}
