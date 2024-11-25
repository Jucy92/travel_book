package travel_book.service.domain.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class VideoScraper {
    public static void main(String[] args) throws Exception {
        //String url = "https://www.inflearn.com/course/lecture?courseSlug=%EA%B9%80%EC%98%81%ED%95%9C%EC%9D%98-%EC%8B%A4%EC%A0%84-%EC%9E%90%EB%B0%94-%EA%B3%A0%EA%B8%89-2&unitId=244465&subtitleLanguage=ko";
        String url = "https://www.youtube.com/watch?v=6GXQ2AuClwQ";
        Document doc = Jsoup.connect(url).get();
        System.out.println("doc = " + doc);

        Elements allElements = doc.getElementsByClass("css-1w3r31x");
        System.out.println("allElements = " + allElements);
        Element videoElement = doc.select("data-unit-title").first();
        System.out.println("videoElement = " + videoElement);

        String videoUrl = videoElement.attr("href");
        
        System.out.println("Video URL: " + videoUrl);
    }
}