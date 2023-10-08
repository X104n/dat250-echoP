package VotingApp;

import VotingApp.vote.Vote;
import VotingApp.vote.VoteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VotingApp {
    public static void main(String[] args) {
        SpringApplication.run(VotingApp.class, args);
    }

}
