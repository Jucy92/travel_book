package travel_book.service.web.api.perplexity;

import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
public class PerplexityApiModel {
    private String destination;     // 여행 목적지
    private String itinerary;       // 여행 일정(스케줄)
    //private String content1;         // 프롬프트 내용(perplexity) - system  => 딱히 필요하지 않아서 사용 안하지만, 나중을 위해 일단 냅둠 (사용X)
    //private String content2;         // 질문 내용(perplexity) - user
    private String responseContent;  // 응답 받은 내용
}
