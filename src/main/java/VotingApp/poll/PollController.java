package VotingApp.poll;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PollController {

    @Autowired
    private PollDAO pollDAO;

    @PostMapping("/add")
    public String addPoll(@RequestBody Poll poll) {
        try {
            pollDAO.addPoll(poll);
            return "Poll added successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
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

    @GetMapping("/polls")
    public ResponseEntity<?> getAllPolls() {
        try {
            List<Poll> polls = pollDAO.getAllPolls();
            return ResponseEntity.ok(polls);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/poll/{id}")
    public ResponseEntity<?> updatePoll(
            @PathVariable Long id,
            @RequestBody Poll updatedPoll
    ) {
        try {
                pollDAO.updatePoll(id, updatedPoll);
                return ResponseEntity.ok("Poll updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }


    @DeleteMapping("/poll/{id}")
    public ResponseEntity<?> deletePollById(@PathVariable Long id) {
        try {
            pollDAO.deletePollById(id);
            return ResponseEntity.ok("Poll deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }



}
