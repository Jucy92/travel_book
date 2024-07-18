package travel_book.service.web.map.service;

import org.springframework.stereotype.Service;
import travel_book.service.web.map.dao.MapMapper;
import travel_book.service.web.map.dto.LocationModel;
import travel_book.service.web.map.dto.LocationSearchCond;

import java.util.List;

@Service
public class MapService {

    private final MapMapper mapMapper;

    public MapService(MapMapper mapMapper) {
        this.mapMapper = mapMapper;
    }

    public void saveLocation(LocationModel location) {  // 좌표 저장
        mapMapper.saveLocation(location);
    }

    public List<LocationModel> LocationFindById(Long memberId) {         // 저장된 좌표 불러오기
        return mapMapper.LocationFindById(memberId);
    }
}
