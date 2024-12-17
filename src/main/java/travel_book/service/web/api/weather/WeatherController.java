package travel_book.service.web.api.weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @PostMapping("/weather")
    public String getWeather(@RequestBody Map<String, Double> coordinate) {
        return weatherService.getWeatherData(coordinate.get("lat"), coordinate.get("lng"));
    }
}
