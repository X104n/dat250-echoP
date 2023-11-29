package VotingApp.user;

import VotingApp.poll.Poll;
import VotingApp.vote.Vote;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Collection;
import java.util.ArrayList;

@Entity
@Table(name = "app_user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String name;
    private String email;
    private String password;
    private Boolean isAdmin;

    @OneToMany(mappedBy = "createdBy")
    @JsonIncludeProperties("id")
    private Collection<Poll> polls = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIncludeProperties("voteID")
    private Collection<Vote> votes = new ArrayList<>();
}