package VotingApp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Analytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long analyticsID;

    private Long linkedPollID;
    private Integer totalGreenVotes;
    private Integer totalRedVotes;
    private java.sql.Timestamp createdDate;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;
}


