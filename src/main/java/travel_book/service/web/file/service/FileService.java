package travel_book.service.web.file.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private static final String UPLOAD_DIR; // = "C:/uploads"; // 저장 경로 설정
    static {
    String os = System.getProperty("os.name").toLowerCase();
    if (os.contains("win")) {
        UPLOAD_DIR = "C:/uploads"; // Windows 경로
    } else {
        UPLOAD_DIR = "/home/username/uploads"; // 리눅스 경로
    }
}


    public List<String> saveFiles(MultipartFile[] files) {
        List<String> fileNames = new ArrayList<>();
        File uploadDir = new File(UPLOAD_DIR);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    File destFile = new File(UPLOAD_DIR, fileName);
                    file.transferTo(destFile);
                    fileNames.add(fileName);
                } catch (IOException e) {
                    throw new RuntimeException("파일 저장 실패: " + file.getOriginalFilename(), e);
                }
            }
        }
        return fileNames;
    }

}
