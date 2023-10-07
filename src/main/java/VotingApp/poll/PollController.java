package VotingApp.poll;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PollController {
    private Long idCounter;
    private List<Poll> polls = new ArrayList<>();
    public static final String POLL_WITH_THE_ID_X_NOT_FOUND = "Poll with the id %s not found!";


    @GetMapping("/polls")
    public List<Poll> getPolls() {
        return polls;
    }

    @PostMapping("/polls")
    public Poll createPoll(@RequestBody Poll poll){
        poll.setId(++idCounter);
        polls.add(poll);
        return poll;
    }

    @GetMapping("/polls/{id}")
    public Object getPollById(@PathVariable Long id) {
        // loops through list until I find the poll with the id
        for (Poll poll : polls) {

            if (poll.getId().equals(id)) {
                return poll;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(POLL_WITH_THE_ID_X_NOT_FOUND, id));
    }

    @PutMapping("/polls/{id}")
    public Object updatePollById(@PathVariable Long id) {
        // loops through list until I find the todo with the id

        for (Poll poll : polls) {
            if (poll.getId().equals(id)) {
                return poll;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(POLL_WITH_THE_ID_X_NOT_FOUND, id));
    }

    @DeleteMapping("/polls/{id}")
    public void deletePollById(@PathVariable Long id) {
        // loops through list until I find the poll with the id
        for (Poll poll : polls) {
            if (poll.getId().equals(id)) {
                polls.remove(poll);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(POLL_WITH_THE_ID_X_NOT_FOUND, id));
    }




}
