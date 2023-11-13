package VotingApp.IotDevice;
import VotingApp.vote.VoteDAO;

import VotingApp.vote.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IoTController {

    @Autowired
    private VoteDAO voteDAO;

    @PostMapping("/iot")
    public ResponseEntity<Vote> addVote(@RequestBody Vote vote) {
        try {
            voteDAO.addVote(vote);
            return new ResponseEntity<>(vote, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
