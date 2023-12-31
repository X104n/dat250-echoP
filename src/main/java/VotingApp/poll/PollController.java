package VotingApp.poll;


import VotingApp.security.JWS;
import VotingApp.user.UserDAO;
import VotingApp.vote.Vote;
import VotingApp.user.User;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONParser;
import org.h2.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class PollController {

    @Autowired
    private PollDAO pollDAO;

    @Autowired
    private JWS JWS;

    @Autowired
    private UserDAO userDAO;

    @PostMapping("/poll")
    public ResponseEntity<Poll> addPoll(@RequestBody Poll poll, @RequestHeader("Authorization") String token) {
        try {
            User user = JWS.getUserFromToken(token);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            poll.setCreatedBy(user);
            pollDAO.addPoll(poll);
            return new ResponseEntity<>(poll, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/poll")
    public ResponseEntity<List<Poll>> getAllPolls() {
        try {
            List<Poll> polls = pollDAO.getPolls();
            if (polls != null) {
                return new ResponseEntity<>(polls, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/poll/{id}")
    public ResponseEntity<?> getPollById(@PathVariable Long id) {
        try {
            Poll poll = pollDAO.getPollById(id);
            if (poll != null) {
                return ResponseEntity.ok(poll);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/poll")
    public ResponseEntity<String> updatePoll(@RequestBody Poll updatedPoll, @RequestHeader("Authorization") String token) {
        try {
            Long id = updatedPoll.getId();
            User currentUser = JWS.getUserFromToken(token);
            Poll existingPoll = pollDAO.getPollById(id);
            if (existingPoll == null) {
                return new ResponseEntity<>("Poll not found", HttpStatus.NOT_FOUND);
            }
            if (currentUser.getName().equals(existingPoll.getCreatedBy().getName()) || currentUser.getIsAdmin()) {
                pollDAO.updatePoll(id, updatedPoll);
                return new ResponseEntity<>("Poll updated successfully!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/poll")
    public ResponseEntity<String> deletePollById(@RequestHeader("Authorization") String token, @RequestBody Poll poll) {
        try {
            Long id = poll.getId();
            User currentUser = JWS.getUserFromToken(token);
            Poll existingPoll = pollDAO.getPollById(id);
            if (existingPoll == null) {
                return new ResponseEntity<>("Poll not found", HttpStatus.NOT_FOUND);
            }
            if (currentUser.getName().equals(existingPoll.getCreatedBy().getName()) || currentUser.getIsAdmin()) {
                pollDAO.deletePoll(existingPoll);
                return new ResponseEntity<>("Poll deleted successfully!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/iot/{id}")
    public ResponseEntity<?> getGreenAndRedVotes(@PathVariable Long id) {
        try {
            Poll poll = pollDAO.getPollById(id);
            if (poll != null) {
                List<Integer> votes = new ArrayList<>();
                votes.add(poll.getGreenVotes());
                votes.add(poll.getRedVotes());
                return ResponseEntity.ok(votes);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }

    }
}

