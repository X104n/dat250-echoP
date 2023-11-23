package VotingApp.poll;

import VotingApp.mqtt.MqttService;
import VotingApp.dweet.DweetService; // Import the DweetService
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

    // Method to activate new polls
    @Scheduled(fixedRate = 60000) // Runs every 60 seconds as an example
    public void activateNewPolls() {
        List<Poll> pollsToActivate = pollDAO.getPollsToActivate();
        for (Poll poll : pollsToActivate) {
            poll.setRequireLogin(true);
            pollDAO.updatePoll(poll.getId(), poll);
            Map<String, Object> startData = new HashMap<>();
            startData.put("id", poll.getId());
            startData.put("event", "start");
            dweetService.sendDweet("my_poll_event_" + poll.getId(), startData);
        }
    }

    // Method to close expired polls and publish results
    @Scheduled(fixedRate = 60000)
    public void closeExpiredPolls() {
        List<Poll> pollsToClose = pollDAO.getPollsToEnd();
        for (Poll poll : pollsToClose) {
            poll.setRequireLogin(false);
            pollDAO.updatePoll(poll.getId(), poll);

            // Notify the poll end event via dweet.io
            Map<String, Object> endData = new HashMap<>();
            endData.put("id", poll.getId());
            endData.put("event", "end");
            dweetService.sendDweet("my_poll_event_" + poll.getId(), endData);

            // Publish poll results via MQTT
            String pollResults = getPollResults(poll);
            try {
                mqttService.publish("polls/results", pollResults);
            } catch (MqttException e) {
                System.err.println("Failed to publish poll results: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private String getPollResults(Poll poll) {
        // Logic to format and return poll results as a String
        // This could be a JSON representation or any other format suitable for your subscribers
        String result = "Poll results for " + poll.getTitle() + "\n" + poll.getGreenVotes() + " green votes\n" + poll.getRedVotes() + " red votes";
        return result;
    }
}
