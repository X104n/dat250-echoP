package VotingApp.poll;

import VotingApp.user.User;
import VotingApp.vote.Vote;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "polls")
@Getter
@Setter
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pollID;
    private String title;
    private String question;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private Boolean isPublic;
    private String pollLink;
    private String pollCode;
    private int redVotes;
    private int greenVotes;


    @ManyToOne
    @JoinColumn(name = "created_by_user")
    private User createdBy;
















}


