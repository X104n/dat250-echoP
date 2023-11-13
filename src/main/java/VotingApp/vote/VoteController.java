package VotingApp.vote;

import VotingApp.poll.Poll;
import VotingApp.poll.PollDAO;
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

    @Autowired
    private PollDAO pollDAO;

    @PostMapping("/vote")
    public ResponseEntity<Vote> addVote(@RequestBody Vote vote) {
        try {
            voteDAO.addVote(vote);
            pollDAO.addGreenAndRedVotes(vote);
            return new ResponseEntity<>(vote, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/vote")
    public ResponseEntity<Vote> updateVote(@RequestBody Vote vote) {
        try {
            Vote oldVote = voteDAO.getVoteById(vote.getVoteID());
            if (oldVote.getChoice() != vote.getChoice()) {
                pollDAO.deleteGreenAndRedVotes(oldVote);
                pollDAO.addGreenAndRedVotes(oldVote);
            }
            pollDAO.addGreenAndRedVotes(vote);

            voteDAO.updateVote(vote);

            return new ResponseEntity<>(vote, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vote")
    public ResponseEntity<List<Vote>> getVotes() {
        try {
            List<Vote> votes = voteDAO.getVotes();
            if (votes != null) {
                return new ResponseEntity<>(votes, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vote/id/{id}")
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

    @GetMapping("/vote/poll/{Poll}")
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
    @GetMapping("/vote/user/{User}")
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
    @DeleteMapping("/vote/{id}")
    public ResponseEntity<HttpStatus> deleteVotebyID(@PathVariable Long id) {
        try {
            Vote vote = voteDAO.getVoteById(id);
            pollDAO.deleteGreenAndRedVotes(vote);
            voteDAO.deleteVoteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/vote")
    public ResponseEntity<HttpStatus> deleteVote(@RequestBody Vote vote) {
        try {
            pollDAO.deleteGreenAndRedVotes(vote);
            voteDAO.deleteVote(vote);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
