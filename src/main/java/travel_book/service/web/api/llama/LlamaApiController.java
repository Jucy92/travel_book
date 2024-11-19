package travel_book.service.web.api.llama;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class LlamaApiController {
    @GetMapping("/api/llama")
    public String getLlamaModel() throws IOException {

        String apiKey = "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";

        // llama 모델 로드
        LlamaModelLoader loader = new LlamaModelLoader("C:/Users/user/.ollama/llama3.1:8b.model", "192.168.0.100", 8080);
        String model = loader.loadLlamaModel();

        return model;
    }
}