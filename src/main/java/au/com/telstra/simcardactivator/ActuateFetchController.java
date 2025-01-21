package au.com.telstra.simcardactivator;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ActuateFetchController {

    private final RestTemplate restTemplate;

    public ActuateFetchController() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> fetchActuateService( String uri, Map<String, Object> body ) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> actuateRequest = new HttpEntity<>(body, headers);
        System.out.println("Request body to the actuate Service: \t" + body);
        System.out.println("Request headers to the actuate Service: \t" + headers);
        System.out.println("Send request to uri: \t" + uri);

        Map<String, Object> actuateResponse = restTemplate.postForObject( uri, actuateRequest, Map.class );

        System.out.println("Response from the actuate Service: \t" + actuateResponse);

        return actuateResponse;
    }
}
