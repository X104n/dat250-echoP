package VotingApp.poll;

import VotingApp.mqtt.MqttService;
import VotingApp.dweet.DweetService; // Import the DweetService
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
@EnableScheduling
public class PollService {

    @Autowired
    private PollDAO pollDAO;
    @Autowired
    private MqttService mqttService;
    @Autowired
    private DweetService dweetService; // Autowire DweetService

    private final Gson gson = new Gson(); // Create a Gson instance

    // Method to activate new polls
    @Scheduled(fixedRate = 60000)
    public void activateNewPolls() {
        List<Poll> pollsToActivate = pollDAO.getPollsToActivate();
        for (Poll poll : pollsToActivate) {
            poll.setRequireLogin(true);
            pollDAO.updatePoll(poll.getId(), poll);

            Map<String, Object> startData = new HashMap<>();
            startData.put("id", poll.getId());
            startData.put("event", "start");
            String jsonData = gson.toJson(startData); // Convert to JSON

            dweetService.sendDweet("my_poll_event_" + poll.getId(), jsonData);
        }
    }
    @Scheduled(fixedRate = 60000)
    // Method to close expired polls and publish results
    public void closeExpiredPolls() {
        List<Poll> pollsToClose = pollDAO.getPollsToEnd();
        for (Poll poll : pollsToClose) {
            if (poll.getIsProcessed()) {
                continue;
            }

            Map<String, Object> endData = new HashMap<>();
            endData.put("id", poll.getId());
            endData.put("event", "end");
            String jsonData = gson.toJson(endData); // Convert to JSON

            dweetService.sendDweet("my_poll_event_" + poll.getId(), jsonData);

            // Publish poll results via MQTT
            String pollResults = getPollResults(poll);
            try {
                mqttService.publish("polls/results", pollResults);
            } catch (MqttException e) {
                System.err.println("Failed to publish poll results: " + e.getMessage());
                e.printStackTrace();
            }

            poll.setIsActive(false); // Mark the poll as inactive if applicable
            poll.setIsProcessed(true); // Set isProcessed to true
            pollDAO.updatePoll(poll.getId(), poll); // Persist the changes
        }
    }

    @PostConstruct
    public void init() {
        try {
            mqttService.subscribeToPollResults();
        } catch (MqttException e) {
            // Handle exception
        }
    }

    private String getPollResults(Poll poll) {
        Gson gson = new Gson();
        Map<String, Object> result = new HashMap<>();
        result.put("pollTitle", poll.getTitle());
        result.put("greenVotes", poll.getGreenVotes());
        result.put("redVotes", poll.getRedVotes());

        return gson.toJson(result);
    }

}
