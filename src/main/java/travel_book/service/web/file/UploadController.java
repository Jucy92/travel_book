package travel_book.service.web.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import travel_book.service.web.file.service.FileService;
import travel_book.service.web.map.dto.TravelBasicData;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller //@RestController 화면 호출때문에.. 변경 
//@RequestMapping("/files")
@RequiredArgsConstructor
@Slf4j
public class UploadController { // 이것도 그냥 FileController로 변경하고 여기서 Up/Down 다 처리할까..?

    private final FileService fileService;

    @GetMapping("/files/upload")
    public String showUploadForm() {
        return "/upload/uploadForm";
    }

    @ResponseBody
    @PostMapping("/files/upload")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        List<String> fileNames = fileService.saveFiles(files);
        log.info("saveFiles={}",fileNames);
        List<String> imageList = fileService.getAllImages();
        Map<String, Object> response = new HashMap<>();
        response.put("uploadedFiles", fileNames);
        response.put("imageList", imageList);
        //return ResponseEntity.ok().body(Map.of("uploadedFiles", fileNames,"imageList",imageList));    // 이런식으로 10개까지 되기는 하지만..
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/images/{imageName}")
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable("imageName") String imageName) {
        try {
            // 이미지 반환 요청
            Resource resource = fileService.loadImage(imageName);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg") // 미디어(MIME) 타입 설정
                    .body(resource);

        } catch (MalformedURLException e) {
            log.info("MalformedURLException={}",e);
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseBody
    @PostMapping("/fileUpload/travelImage")
    public ResponseEntity<?> travelImageUpload(@RequestParam("file") MultipartFile file, TravelBasicData travelBasicData/*@RequestParam Map<String, Object> requestMap*/) {
        log.info("file={}", file);
        log.info("travelDetail={}", travelBasicData);
        fileService.saveFile(file, travelBasicData);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", "데이터는 정상으로 받았어요~");
        return ResponseEntity.ok().body(responseMap);
    }

}
