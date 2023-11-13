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

    @PutMapping("/vote/{id}")
    public ResponseEntity<String> updateVote(@PathVariable Long id, @RequestBody Vote updatedVote, @CurrentUser User currentUser) {
        try {
            Vote existingVote = voteDAO.getVoteById(id);
            if (existingVote == null) {
                return new ResponseEntity<>("Vote not found", HttpStatus.NOT_FOUND);
            }
            if (currentUser.getUserID().equals(existingVote.getUser().getUserID()) || currentUser.getIsAdmin()) {
                voteDAO.updateVote(updatedVote);
                return new ResponseEntity<>("Vote updated successfully!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/vote/{id}")
    public ResponseEntity<String> deleteVoteById(@PathVariable Long id, @CurrentUser User currentUser) {
        try {
            Vote existingVote = voteDAO.getVoteById(id);
            if (existingVote == null) {
                return new ResponseEntity<>("Vote not found", HttpStatus.NOT_FOUND);
            }
            if (currentUser.getUserID().equals(existingVote.getUser().getUserID()) || currentUser.getIsAdmin()) {
                voteDAO.deleteVoteById(id);
                return new ResponseEntity<>("Vote deleted successfully!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
