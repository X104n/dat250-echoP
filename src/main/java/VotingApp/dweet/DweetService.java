package VotingApp.dweet;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DweetService {

    private RestTemplate restTemplate = new RestTemplate();

    public void sendDweet(String pollName, Object data) {
        String dweetUrl = "https://dweet.io/dweet/for/" + pollName;
        restTemplate.postForObject(dweetUrl, data, String.class);
    }
}
