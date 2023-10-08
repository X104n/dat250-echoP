package VotingApp.poll;

import VotingApp.user.User;
import VotingApp.vote.Vote;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Collection;

@Entity
@Table(name = "polls")
@Getter
@Setter
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pollId;
    private String title;
    private String question;
    private java.sql.Timestamp startDateTime;
    private java.sql.Timestamp endDateTime;
    private Boolean isPublic;
    private String pollLink;
    private String pollCode;

    @ManyToOne
    @JoinColumn(name = "created_by_user")
    private User createdBy;

    @OneToMany(mappedBy = "poll")
    private Collection<Vote> votes;















}


