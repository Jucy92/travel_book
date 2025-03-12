package travel_book.service.web.file;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import travel_book.service.web.file.service.FileService;

import java.util.List;
import java.util.Map;

@Controller //@RestController 화면 호출때문에.. 변경 
@RequestMapping("/files")
@RequiredArgsConstructor
public class UploadController { // 이것도 그냥 FileController로 변경하고 여기서 Up/Down 다 처리할까..?

    private final FileService fileService;
    @GetMapping("/upload")
    public String showUploadForm() {
        return "/upload/uploadForm";
    }

    @ResponseBody
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        List<String> fileNames = fileService.saveFiles(files);
        return ResponseEntity.ok().body(Map.of("uploadedFiles", fileNames));
    }
}
