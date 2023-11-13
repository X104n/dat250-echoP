package VotingApp.IotDevice;

import VotingApp.poll.Poll;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class IoTDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceID;

    private String deviceType;

    private Long linkedPollID;

    @ManyToOne
    @JoinColumn(name = "linked_poll_id")
    private Poll poll;
}
