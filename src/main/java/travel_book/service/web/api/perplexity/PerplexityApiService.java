package travel_book.service.web.api.perplexity;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class PerplexityApiService {

    // PerplexityApiModel
//    @Value("${pplx-xxx}")    // 이렇게 쓰는게 아니라.. application.properties 에 명시..
    @Value("${perplexity.api.key}")
    private String apiKey;
    private String requestURL = "https://api.perplexity.ai/chat/completions";

    private final OkHttpClient client = new OkHttpClient();
    // RestTemplate 자바 기본으로 제공하는 API -> 다음에는 이거 사용해보기


    public String requestQuery(PerplexityApiModel model) throws IOException {
        model.setContent1("From now on, your job is a travel planner who runs a travel agency. Collect as much information as possible and make a detailed travel plan according to your request. Please translate the answer into Korean at the end");
        // ㄴ> model.setContent1("지금부터 너의 직업은 여행사를 운영하고 있는 여행 플래너야 최대한 많은 정보를 수집해서 요청 사항에 맞게 상세한 여행 계획을 만들어 주도록 해 마지막에 답변은 한국어로 변환해서 말해줘");
        model.setContent2(model.getDestination() + "쪽으로 여행을 가려고 해.\n" + model.getItinerary() + "요청사항에 맞춰서 여행 계획을 만들어줘");
        String prompt = model.getContent2();

        MediaType mediaType = MediaType.parse("application/json");      // 내가 보내는 타입 설정?

        RequestBody body = RequestBody.create("{"
                + "\"model\": \"mistral-7b-instruct\","
                + "\"messages\": [{\"role\": \"system\", \"content\": \"" + model.getContent1() + "\"}, {\"role\": \"user\", \"content\": \"" + prompt + "\"}]"
                + "}", mediaType);

        Request request = new Request.Builder()
                .url(requestURL)
                .post(body) // 여기서 오류가 뜨는거 같은데.. 안에 값을 볼 수가 없네...?
                .addHeader("Accept", "application/json")    // 생략해도 되려나 API 요청사항에는 적혀 있었는데 지피티에는 없었음
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        //log.info("APIKey={}",apiKey);     // 정상적으로 잘 찍힘
        log.info("body={}", body.toString());
        log.info("request={}",request);

        /*
        // 동기 처리시 execute함수 사용
        try (Response response = client.newCall(request).execute()) {   // 클라이언트 새로만들면서 request에 담긴 정보 실행하고 / response에 응답 내용 담아와서 받은 내용 requestQuery 호출한 쪽에 반환?
            log.info("응답 내용={}",response);
            log.info("response.body().string()={}",response.body().string());
            return response.body().string();
        }
        */
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("error + Connect Server Error is " + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println("Response Body is " + response.body().string());
            }
        }) ;

        return null;
    }
}

    /** perplexity API 요청 가이드
     * curl -X POST \
     *      --url https://api.perplexity.ai/chat/completions \
     *      --header 'accept: application/json' \
     *      --header 'content-type: application/json' \
     *      --header "Authorization: Bearer ${PERPLEXITY_API_KEY}" \
     *      --data '{
     *   	  "model": "mistral-7b-instruct",
     *        "stream": false,
     *        "max_tokens": 1024,
     *        "frequency_penalty": 1,
     *        "temperature": 0.0,
     *      "messages": [
     *        {
     * 			"role": "system",
     * 			"content": "Be precise and concise in your responses."
     *        },
     *        {
     * 			"role": "user",
     * 			"content": "How many stars are there in our galaxy?"
     *        }
     * 	     ]
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