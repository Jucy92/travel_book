package travel_book.service.web.map.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import travel_book.service.domain.map.LocationDetail;

public interface LocationDetailRepository extends JpaRepository<LocationDetail, Long> {

}