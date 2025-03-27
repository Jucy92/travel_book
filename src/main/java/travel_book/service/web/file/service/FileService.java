package travel_book.service.web.file.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import travel_book.service.web.file.dao.FileMapper;
import travel_book.service.web.file.dto.TravelFileData;
import travel_book.service.web.map.dto.TravelBasicData;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {
    private static final String UPLOAD_DIR; // = "C:/uploads"; // 저장 경로 설정

    static {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            UPLOAD_DIR = "C:/files/"; // Windows 경로
        } else {
            UPLOAD_DIR = "/home/username/files/"; // 리눅스 경로
        }
    }

    private final FileMapper fileMapper;
    //private final

    public List<String> saveFiles(MultipartFile[] files) {
        List<String> fileNames = new ArrayList<>();
        File uploadDir = new File(UPLOAD_DIR);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
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


    public String saveFile(MultipartFile file, TravelBasicData travelBasicData) {

        String uploadPath = UPLOAD_DIR + "travel/"; //Paths.get(UPLOAD_DIR + "travel\\").toString();
        File directory = new File(uploadPath);  // 디렉토리 유무 없으면 아래서 생성
        TravelFileData travelFileData = new TravelFileData();

        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));// 마지막에 . 이 찍힌 인덱스 구한 후 그쪽에서 substring
        // ㄴ> .(확장자) 없는 파일 업로드 경우 -1 리턴하면서 오류 발생하는데.. 예외처리해야 할듯 한데
        String fileName = UUID.randomUUID() + ext;  // UUID로 중복되지 않게 생성

        travelFileData.setTravelId(travelBasicData.getTravelId());
        travelFileData.setLocationId(travelBasicData.getLocationId());
        travelFileData.setLocationDetailId(travelBasicData.getLocationSq());
        travelFileData.setOriginalFileName(originalFilename);
        travelFileData.setFileName(fileName);
        travelFileData.setFilePath(uploadPath);
        travelFileData.setFileSize(file.getSize());
        travelFileData.setMimeType(file.getContentType());

        log.info("travelFileData={}", travelFileData);


        if (!directory.exists()) {  // 디렉토리 유무 확인
            if (directory.mkdirs()) log.info("폴더 생성={}",directory);
            else log.info("폴더 생성 실패={}",directory);
        }

        File destFile = new File(uploadPath, fileName);
        try {
            file.transferTo(destFile);      // 실제 파일 저장경로에 저장   -> 여기서 성공하고 아래서 실패하면 롤백하고 싶은데.. @Transactional 추가해도 안되네
            fileMapper.fileDataSave(travelFileData);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패: " + travelFileData, e);
        }

        return "OK";
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
