package travel_book.service.web.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
public class APIController {

    @PostMapping("/api/ai-recommendation")
    public ResponseEntity<Map<String, String>> getAIRecommendation(@RequestBody Map<String, String> request) throws IOException {
        String destination = request.get("destination");    // 여행지
        String itinerary = request.get("itinerary");        // 여행 요청 내용

        String apiKey = "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";
        String query = destination + "로 여행을 가려고 하는데" + itinerary + "에 맞춰서 가이드가 되어서 여행 계획을 만들어줘";
        StringBuffer responseStr = null;

        /*
         */
        URL url = new URL("https://llama3-8b.com/api/v1/questions");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        connection.setDoOutput(true);
        byte[] outputBytes = ("apiKey=" + apiKey + "&query=" + query).getBytes();
        connection.getOutputStream().write(outputBytes);

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            responseStr = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                responseStr.append(inputLine);
                System.out.println("inputLine = " + inputLine);
            }
            in.close();
            //JSONObject jsonpObject = new JSONObject(responseStr.toString());

        } else {
            System.out.println("Failed HTTP error code : " + responseCode);
        }

        // 여기에 AI API 호출 또는 추천 로직을 추가합니다.
        String recommendation = "AI가 추천하는 " + destination + "의 일정: " + itinerary;

        Map<String, String> response = new HashMap<>();
        response.put("recommendation", recommendation);
        //response.put("recommendation", responseStr.toString());

        return ResponseEntity.ok(response);
    }
}
