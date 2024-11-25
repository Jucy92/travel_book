package travel_book.service.domain.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class YouTubeInfoExtractor {

    public static void main(String[] args) {
        String videoUrl = "https://www.youtube.com/watch?v=6GXQ2AuClwQ";
        
        try {
            Document doc = Jsoup.connect(videoUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .get();

            System.out.println("doc = " + doc);

            // 동영상 제목 추출
            Element titleElement = doc.select("meta[name=title]").first();
            String title = titleElement != null ? titleElement.attr("content") : "제목을 찾을 수 없습니다.";

            // 동영상 설명 추출
            Element descriptionElement = doc.select("meta[name=description]").first();
            String description = descriptionElement != null ? descriptionElement.attr("content") : "설명을 찾을 수 없습니다.";

            // 동영상 주소 추출
            Elements videoElement = doc.select("meta[property=og:url]");
            String video = videoElement != null ? videoElement.attr("content") : "비디오를 찾을 수 없습니다.";

            // 결과 출력
            System.out.println("제목: " + title);
            System.out.println("설명: " + description);
            System.out.println("비디오링크: " + video);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}