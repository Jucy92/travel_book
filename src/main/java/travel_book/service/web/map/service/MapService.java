package travel_book.service.web.map.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import travel_book.service.web.map.dao.MapMapper;
import travel_book.service.web.map.dto.LocationModel;
import travel_book.service.web.map.dto.LocationSearchCond;
import travel_book.service.web.map.dto.RouteResponse;

import java.util.List;

@Service
public class MapService {

    /*      // 카카오맵 도보 경로 받아오기 위해 선언했던 내용
    @Value("${appkey=31392fb12fa81362020946d15afde9c2}")
    private String kakaoApiKey;
    */

    private final MapMapper mapMapper;

    public MapService(MapMapper mapMapper) {
        this.mapMapper = mapMapper;
    }

    public void saveLocation(LocationModel location) {  // 좌표 및 입력 데이터(여행계획) 저장
        mapMapper.saveLocation(location);
    }
    public int saveStorageM(LocationModel location) {  // 여행 계획 마스터
        return mapMapper.saveStorageM(location);
    }
    public void saveStorageD(LocationModel location) {  // 여행 계획 디테일
        mapMapper.saveStorageD(location);
    }

    public List<LocationModel> LocationFindById(Long memberId) {         // 저장된 좌표 불러오기
        return mapMapper.LocationFindById(memberId);
    }
    public List<LocationModel> storageMFindByUserId(String userId) {         // 저장된 좌표 불러오기
        return mapMapper.storageMFindByUserId(userId);
    }


    /*      // 카카오맵 도보 경로 받아오기 위해 선언했던 내용
    // 도보 경로 검색
    public RouteResponse getWalkingRoute(double startLat, double startLng, double endLat, double endLng) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://apis-navi.kakaomobility.com/v1/walking?origin=%f,%f&destination=%f,%f",
                startLng, startLat, endLng, endLat);
        return restTemplate.getForObject(url, RouteResponse.class);
    }

   */
}
