package VotingApp.vote;

import VotingApp.poll.Poll;
import VotingApp.user.User;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
    @JoinColumn(name = "name")
    @JsonIncludeProperties("name")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id")
    @JsonIncludeProperties("id")
    private Poll poll;
}
