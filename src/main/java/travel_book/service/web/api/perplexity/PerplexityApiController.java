package travel_book.service.web.api.perplexity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PerplexityApiController {

    private final PerplexityApiService apiClient;

    //@Autowired    // 이게 없으면 컨테이너에 등록되어있더라도 주입을 못하겠지..?
    public PerplexityApiController(PerplexityApiService apiClient) {
        this.apiClient = apiClient;
    }
    // 웹 질의 -> 컨트롤러 -> API 호출 -> API 응답 -> 웹 응답
    @PostMapping("/api/perplexity")
    public PerplexityApiModel ApiRequest(@RequestBody PerplexityApiModel model) throws IOException {
        apiClient.requestQuery(model);

        return model;
    }
}
