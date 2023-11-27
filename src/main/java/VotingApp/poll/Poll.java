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
    private Long id;
    private String title;
    private String question;
    private Timestamp startDate;
    private Timestamp endDate;
    private Boolean requireLogin;
    private String pollLink;
    private String pollCode;
    private int redVotes;
    private int greenVotes;
    private Boolean isActive;
    private Boolean isProcessed = false;


    protected int resultGreen(){
        return greenVotes;
    }

    protected int resultRed(){
        return redVotes;
    }


    @ManyToOne
    @JoinColumn(name = "created_by_user")
    private User createdBy;

    @OneToMany(mappedBy = "poll")
    private Collection<Vote> votes;















}


