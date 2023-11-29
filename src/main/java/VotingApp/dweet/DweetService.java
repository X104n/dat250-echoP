package VotingApp.dweet;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DweetService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String sendDweet(String pollName, String jsonData) {
        String dweetUrl = "https://dweet.io/dweet/for/" + pollName;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonData, headers);

        try {
            // Send the request and receive the response
            return restTemplate.postForObject(dweetUrl, entity, String.class);
        } catch (Exception e) {
            // Log the exception
            return "Failed to send dweet: " + e.getMessage();
        }
    }
}
