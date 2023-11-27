package VotingApp.vote;

import VotingApp.poll.Poll;
import VotingApp.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteID;

    private Boolean choice;

    @ManyToOne
    private User user;

    @ManyToOne
    private Poll poll;
}
