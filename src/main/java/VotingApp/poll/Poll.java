package VotingApp.poll;

import VotingApp.user.User;
import VotingApp.vote.Vote;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Collection;

@Entity
@Getter
@Setter
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }





}
