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

        return restTemplate.postForObject( uri, actuateRequest, Map.class );
    }
}
