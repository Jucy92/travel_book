package travel_book.service.web.api.weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WeatherService {
    @Value("${openweathermap.api.key}")
    private String apiKey;

    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={apiKey}&units=metric";

    private final RestTemplate restTemplate;
    public WeatherService() {
        this.restTemplate = new RestTemplateBuilder()
            .setConnectTimeout(Duration.ofMillis(3000))
            .setReadTimeout(Duration.ofMillis(3000))
            .build();
    }
    public String getWeatherData(double lat, double lon) {
        log.info("restTemplate.getForObject={}", restTemplate.getForObject(WEATHER_API_URL, String.class, lat, lon, apiKey)); // URL에 값 넘겨주면 응답 값 받음
        return restTemplate.getForObject(WEATHER_API_URL, String.class, lat, lon, apiKey);


    }
}
