package travel_book.service.web.api.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // (@JsonIgnoreProperties) - JSON 데이터 Java 객체로 역직렬화
public class WeatherModel {                 // (ignoreUnknown = true) - JSON 데이터에 있지만, 클래스에 없는 매핑은 무시하는 옵션
    private Coord coord;
    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private String name;
    public WeatherModel() {

    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private class Coord {
        private double lat;     // 위도
        private double lon;     // 경도


    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private class Weather {         // 날씨 상태
        private int id;             // 날씨 상태 코드
        private String main;        // 주요 날씨 상태 (Clear - 맑음)
        private String description; // 상세 날씨 설명 (clear sky - 맑은 하늘)
        private String icon;        // 날씨 아이콘 코드
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private class Main {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private int pressure;
        private int humidity;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private class Wind {
        private double speed;
        private int deg;
        private double gust;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private class Clouds {
        private int all;
    }
}
