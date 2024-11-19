package travel_book.service.web.map.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import travel_book.service.web.map.dao.MapMapper;
import travel_book.service.web.map.dto.LocationModel;
import travel_book.service.web.map.dto.LocationSearchCond;
import travel_book.service.web.map.dto.RouteResponse;

import java.util.List;

@Slf4j
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

    public Long saveStorageM(LocationModel location) {  // 여행 계획 마스터
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

    public List<LocationModel> storageDFindByTravelId(Long userId) {         // 저장된 좌표 불러오기
        /*
        // 단일 스레드인지 확인하려고 했는데 Thread:http-nio-8080-exec-1,3 등 요청에 따라 나눠져서 들어옴 
        // 스프링 프레임워크에서 멀티 스레드 지원해주는 듯
        try {
            
              for (int i = 0; i < 10; i++) {
                Thread.currentThread().getState();
                System.out.println("Thread:" + Thread.currentThread().getName() + " 대기시간 :" + i + "초" + " 상태:" + Thread.currentThread().getState());
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            log.info("storageDFindByTravelId 화면에서 오류 ={}", e);
        } finally {
            return mapMapper.storageDFindByTravelId(userId);

        }
        */
        return mapMapper.storageDFindByTravelId(userId);
    }

    public void copyOfAllItinerary(String userId, Long travelId) {
        mapMapper.copyOfAllItinerary(userId, travelId);
        // 퍼갈 때 필요한 정보들 DTO로 따로 뺴서 만들어야 할듯 - 여행제목,
    }

}
