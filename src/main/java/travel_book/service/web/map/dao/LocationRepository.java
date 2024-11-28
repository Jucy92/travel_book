package travel_book.service.web.map.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import travel_book.service.domain.map.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

}