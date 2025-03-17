package travel_book.service.web.file.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileService {
    private static final String UPLOAD_DIR; // = "C:/uploads"; // 저장 경로 설정

    static {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            UPLOAD_DIR = "C:/uploads/"; // Windows 경로
        } else {
            UPLOAD_DIR = "/home/username/uploads/"; // 리눅스 경로
        }
    }
    //private final


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
                    file.transferTo(destFile);  // 파일 데이터 저장경로로 저장
                    fileNames.add(fileName);
                } catch (IOException e) {
                    throw new RuntimeException("파일 저장 실패: " + file.getOriginalFilename(), e);
                }
            }
        }
        return fileNames;
    }

    public List<String> getAllImages() {
        Path uploadPath = Paths.get(UPLOAD_DIR);

        try {
            List<String> imageFiles = Files.list(uploadPath)
                    .filter(Files::isRegularFile)   // 디렉터리 제외한 파일만 필터링(파일타입::boolean)
                    .map(Path::getFileName)         // 파일 이름만 가져오기(파일타입::파일타입)
                    .map(Path::toString)            // 파일이름 문자열 변환(파일타입::스트링)
                    .filter(name -> name.matches(".*\\.(jpg|jpeg|png|gif)$"))       // 이미지 파일 확장자 필터링
                    //.map(name -> name.substring(name.indexOf("_") + 1))   //  1741761731759_black.jpg 에서 black 원본 파일명만 가져오기 위해 처리, +1은 _시작하지 않는 파일이 있다면 -1 오류를 뱉기 때문에 막기 위해 추가
                    .collect(Collectors.toList());
            return imageFiles;
        } catch (IOException e) {
            throw new RuntimeException("파일 불러오기 실패: ", e);
        }
    }

    public Resource loadImage(String fileName) throws MalformedURLException {   // URL이 잘못된 형식 예외

        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() || resource.isReadable()) {   // resource 가 존재하거나, 읽을 수 있는 상태 일 때
            return resource;
        } else {
            throw new MalformedURLException("파일을 찾을 수 없습니다.");
        }
    }


}
