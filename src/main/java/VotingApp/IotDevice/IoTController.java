package VotingApp.IotDevice;
import VotingApp.poll.PollDAO;
import VotingApp.vote.VoteDAO;

import VotingApp.vote.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IoTController {

    @Autowired
    private VoteDAO voteDAO;
    @Autowired
    private PollDAO pollDAO;

    @PostMapping("/iot")
    public ResponseEntity<Vote> addVote(@RequestBody Vote vote) {
        try {
            System.out.println(vote.getChoice());
            voteDAO.addVote(vote);
            return new ResponseEntity<>(vote, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/iot/{id}")
    public void addVote(@PathVariable Long id, @RequestBody boolean choice) {
        try {
            System.out.println(choice);
            pollDAO.addGreenAndRedVotes(id, choice);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
