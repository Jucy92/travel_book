package travel_book.service.web.api.perplexity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PerplexityApiService {
    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();



    // PerplexityApiModel
//    @Value("${pplx-xxx}")    // 이렇게 쓰는게 아니라.. application.properties 에 명시..
    @Value("${perplexity.api.key}")
    private String apiKey;
    private String requestURL = "https://api.perplexity.ai/chat/completions";
    // RestTemplate 자바 기본으로 제공하는 API -> 다음에는 이거 사용해보기

    public void requestQuery(PerplexityApiModel model) throws IOException {
        //model.setContent1("From now on, your job is a travel planner who runs a travel agency. Collect as much information as possible and make a detailed travel plan according to your request. Please translate the answer into Korean at the end");
        // ㄴ> model.setContent1("지금부터 너의 직업은 여행사를 운영하고 있는 여행 플래너야 최대한 많은 정보를 수집해서 요청 사항에 맞게 상세한 여행 계획을 만들어 주도록 해 답변은 한국어로 변환해서 말해줘");
        //model.setContent2(model.getDestination() + "쪽으로 여행을 가려고 해.\n" + model.getItinerary() + "요청사항에 맞춰서 여행 계획을 만들어줘");
        //model.setContent2("지금부터 너의 직업은 여행사를 운영하고 있는 여행 플래너야 " + model.getDestination() + "나라에 대해서 최대한 많은 정보를 수집해서 요청사항에 맞게 계획을 만들어주고 만들어 줄 때 한국어로 변환해서 알려줘. \n 요청사항 :" + model.getItinerary());
        // ㄴ> 굳이 이걸 set해서 model 리턴하면서 내부 처리방법을 공개할 필요가 없어보임
        String prompt = "지금부터 너의 직업은 여행사를 운영하고 있는 여행 플래너야 \n" + model.getDestination() +
                        "장소에 대해서 최대한 많은 정보를 수집해서 요청사항에 맞게 계획을 만들어줘.\n 요청사항 :" + model.getItinerary() +
                        "\n 모든 작업이 끝난 후 답변을 줄 때는 반드시 한국어로 변환해서 알려줘";

        /**
         * JSONObject 라이브러리 객체 설정 (유료 자바에선 기본 제공)
         */
        JSONObject bodyContent = new JSONObject();      // requestBody 설정 정보
        bodyContent.put("model", "llama-3.1-sonar-small-128k-online");
        JSONArray messages = new JSONArray();
        //JSONObject systemMessage = new JSONObject();
        //systemMessage.put("role", "system");
        //systemMessage.put("content", model.getContent1());
        //messages.put(systemMessage);
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.put(userMessage);
        bodyContent.put("messages", messages);
        log.info("request content={}", prompt);

        /**
         * REST ful API(okhttp3) 라이브러리 객체 설정
         */
        // Request Body 설정
        MediaType mediaType = MediaType.parse("application/json");      // 내가 보내는 타입 설정?
        RequestBody body = RequestBody.create(bodyContent.toString(), mediaType);

        // Request Header 설정
        Request request = new Request.Builder()
                .url(requestURL)
                .post(body)
                .addHeader("Accept", "application/json")        // 생략해도 되려나 API 요청사항에는 적혀 있었는데 지피티에는 없었음
                //.addHeader("Content-Type", "application/json")  // mediaType를 사용하는 RequestBody에서 이미 선언 됐기 때문에 중복 추가 할 필요가 없다 - Perple
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        /**
         * REST ful API(okhttp3) 라이브러리 전송 타입 설정
        */
        // 동기 처리시 execute 함수 사용
        try (Response response = client.newCall(request).execute()) {   // 클라이언트 새로만들면서 request에 담긴 정보 실행하고 / response에 응답 내용 담아와서 받은 내용 requestQuery 호출한 쪽에 반환?
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            //log.info("응답 내용={}",response);
            String responseBody = response.body().string();
            //log.info("responseBody={}",responseBody);
            JSONObject responseJson = new JSONObject(responseBody);
            //log.info("responseJson={}",responseJson);
            JSONArray choices = responseJson.getJSONArray("choices");
            JSONObject choiceMessage = choices.getJSONObject(0);
            JSONObject message = choiceMessage.getJSONObject("message");
            Object content = message.get("content");
            model.setResponseContent(content.toString());

            //return content.toString();    리턴 타입 void로 변경
        }
        // 비동기 처리시 enqueue 함수 사용    -> 비동기로 처리 했더니(스레드 처럼) 그냥 던지고 연결이 끊겨서 model에 값을 담기 전에 메서드 종료됨
        /*
        client.newCall(request).enqueue(new Callback() {
        @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("error + Connect Server Error is " + e.toString());
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                // 왜 여기서 직접 호출하면 오류가 발생할까 => Exception in thread "OkHttp Dispatcher" java.lang.IllegalStateException: closed
                //System.out.println("Response Body is " + response.body().string());   // 아래서 responseBody 으로 담아서 하면 되는데..

                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                String responseBody = response.body().string();
                System.out.println("Response: " + responseBody);

                // 여기서 JSON 파싱 진행
                JSONObject responseJson = new JSONObject(responseBody);
                System.out.println("responseJson = " + responseJson);
                JSONArray choices = responseJson.getJSONArray("choices");
                System.out.println("choices = " + choices);
                JSONObject choicesMessage = choices.getJSONObject(0);       // 이거랑 choices 값이 똑같이 나오는데 뭔차이지.. index(1)이 있으면 그거는 안나오는건가? 1은 언제생기지?
                System.out.println("choicesMessage = " + choicesMessage);
                JSONObject message = choicesMessage.getJSONObject("message");
                System.out.println("message = " + message);
                Object content = content.get("content");
                System.out.println("content = " + content);
                model.setResponseContent(content.toString());   // 일단 두는데 모델에 안담고 그냥 리턴해서 컨트롤러에서 또 리턴 할 예정
                log.info("response content ={}", model.getResponseContent());
                //responseContent =

            }
        });
        return null;
         */

 /*
        // 우선 타임아웃 시간 늘렸는데 늘린 시간 보다 더 걸릴 경우를 대비해 아래코드를 사용해서 "재시도 매커니즘 구현"
        int maxRetries = 3;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try {
                // API 호출 코드
                break; // 성공 시 루프 종료
            } catch (SocketTimeoutException e) {
                retryCount++;
                if (retryCount == maxRetries) {
                    throw e; // 최대 재시도 횟수 초과 시 예외 발생
                }
                // 재시도 전 잠시 대기
                Thread.sleep(1000 * retryCount);
            }
        }
*/


    }
}

/**
 * perplexity API 요청 가이드
 * curl -X POST \
 * --url https://api.perplexity.ai/chat/completions \
 * --header 'accept: application/json' \
 * --header 'content-type: application/json' \
 * --header "Authorization: Bearer ${PERPLEXITY_API_KEY}" \
 * --data '{
 * "model": "mistral-7b-instruct",
 * "stream": false,
 * "max_tokens": 1024,
 * "frequency_penalty": 1,
 * "temperature": 0.0,
 * "messages": [
 * {
 * "role": "system",
 * "content": "Be precise and concise in your responses."
 * },
 * {
 * "role": "user",
 * "content": "How many stars are there in our galaxy?"
 * }
 * ]
 * }'
 */


/** API 반환 데이터
 * {
 *   "id": "3fbf9a47-ac23-446d-8c6b-d911e190a898",
 *   "model": "mistral-7b-instruct",
 *   "object": "chat.completion",
 *   "created": 1765322,
 *   "choices": [
 *     {
 *       "index": 0,
 *       "finish_reason": "stop",
 *       "message": {
 *         "role": "assistant",
 *         "content": " The Milky Way galaxy contains an estimated 200-400 billion stars.."
 *       },
 *       "delta": {
 *         "role": "assistant",
 *         "content": " The Milky Way galaxy contains an estimated 200-400 billion stars.."
 *       }
 *     }
 *   ],
 *   "usage": {
 *     "prompt_tokens": 40,
 *     "completion_tokens": 22,
 *     "total_tokens": 62
 *   }
 * }

 */