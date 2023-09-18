package VotingApp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationID;

    private String message;
    private String notificationType;
    private java.sql.Timestamp createdDate;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;
}
