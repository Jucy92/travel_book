package travel_book.service.web.file.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class TravelFileData {
    @Setter(AccessLevel.NONE)
    private Long travelFileId;              // 여행 이미지 ID (자동 생성, 기본 키)
    private Long travelId;                  // 해당 이미지가 속한 여행 ID (외래 키, NOT NULL)
    private Long locationId;                // 해당 이미지가 속한 여행 계획 정보 ID (외래 키, NOT NULL)
    private Long locationDetailId;          // 해당 이미지가 속한 여행 계획 상세 정보 ID (외래 키, NOT NULL)
    private String originalFileName;        // 실제 업로드 파일 이름 (NULL)  -> ? 필요할까
    private String fileName;                // 파일 이름 (NOT NULL)
    private String filePath;                // 파일 저장 경로 (NOT NULL)
    private Long fileSize;                // 파일 크기 (NULL 허용)
    private String mimeType;                // 파일 MIME 타입 (NULL 허용)
    private String description;             //   이미지에 대한 설명 (NULL 허용)
    private LocalDateTime createdAt;        // 생성 일시 (기본값: 현재 타임스탬프)
    private LocalDateTime updatedAt;        // 수정 일시 (기본값: 현재 타임스탬프)


}
