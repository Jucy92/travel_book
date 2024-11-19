package travel_book.service.web.api.llama;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LlamaModelLoader {
    private String modelFile;
    private String ip;
    private int port;

    public LlamaModelLoader(String modelFile, String ip, int port) {
        this.modelFile = modelFile;
        this.ip = ip;
        this.port = port;
    }

    public String loadLlamaModel() throws IOException {
        // llama3.1 : 8b 모델 로드
        File file = new File(modelFile);
        if (!file.exists()) {
            throw new FileNotFoundException("파일이 존재하지 않습니다.");
        }

        // 로드된 모델 리턴
        return "llama3.1:8b";
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}