package VotingApp.poll;


import VotingApp.vote.Vote;
import VotingApp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class PollController {

    @Autowired
    private PollDAO pollDAO;

    @PostMapping("/poll")
    public ResponseEntity<Poll> addPoll(@RequestBody Poll poll, @RequestHeader("Authorization") String token) {
        try {
            System.out.println("Token: " + token);
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

    @PutMapping("/poll/{id}")
    public ResponseEntity<String> updatePoll(@PathVariable Long id, @RequestBody Poll updatedPoll, @RequestHeader User currentUser) {
        try {
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

    @DeleteMapping("/poll/{id}")
    public ResponseEntity<String> deletePollById(@PathVariable Long id, @RequestHeader User currentUser) {
        try {
            Poll existingPoll = pollDAO.getPollById(id);
            if (existingPoll == null) {
                return new ResponseEntity<>("Poll not found", HttpStatus.NOT_FOUND);
            }
            if (currentUser.getName().equals(existingPoll.getCreatedBy().getName()) || currentUser.getIsAdmin()) {
                pollDAO.deletePollById(id);
                return new ResponseEntity<>("Poll deleted successfully!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

