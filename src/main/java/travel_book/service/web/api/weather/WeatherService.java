package travel_book.service.web.api.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

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
    public WeatherModel getWeatherData(double lat, double lon) {

        String jsonResponse = restTemplate.getForObject(WEATHER_API_URL, String.class, lat, lon, apiKey);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonResponse, WeatherModel.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing weather data", e);
        }




    }
}
