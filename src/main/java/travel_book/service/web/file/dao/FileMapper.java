package travel_book.service.web.file.dao;

import org.apache.ibatis.annotations.Mapper;
import travel_book.service.web.file.dto.TravelFileData;

@Mapper
public interface FileMapper {
    void fileDataSave(TravelFileData data);
}
