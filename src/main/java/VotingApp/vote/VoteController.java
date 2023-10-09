package VotingApp.vote;

import VotingApp.poll.Poll;
import VotingApp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VoteController {

    @Autowired
    private VoteDAO voteDAO;

    @PostMapping("/vote")
    public String addUser(@RequestBody Vote vote) {
        try {
            voteDAO.addVote(vote);
            return "User added successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/vote")
    public ResponseEntity<List<Vote>> getAllVotes() {
        try {
            List<Vote> votes = voteDAO.getAllVotes();
            if (votes != null) {
                return new ResponseEntity<>(votes, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vote/{id}")
    public ResponseEntity<Vote> getVoteByID(@PathVariable Long id) {
        try {
            Vote vote = voteDAO.getVoteById(id);
            if (vote != null) {
                return new ResponseEntity<>(vote, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle or log the error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vote/{Poll}")
    public ResponseEntity<List<Vote>> getVoteByPoll(@PathVariable Poll poll) {
        try {
            List<Vote> votes = voteDAO.getVotesByPoll(poll);
            if (votes != null) {
                return new ResponseEntity<>(votes, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle or log the error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/vote/{User}")
    public ResponseEntity<List<Vote>> getVoteByUser(@PathVariable User user) {
        try {
            List<Vote> votes = voteDAO.getVotesByUser(user);
            if (votes != null) {
                return new ResponseEntity<>(votes, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle or log the error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
