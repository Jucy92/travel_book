package travel_book.service.web.map.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import travel_book.service.domain.map.Travel;

public interface TravelRepository extends JpaRepository<Travel, Long> {
    // 자.. 이제 연결 관계에 대해서 insert 하게 만들어야하는데.. 걍 각 도메인별로 Jpa 인터페이스 만들고 값 넘겨주면서 순차 저장시키면 되려나..?
}
