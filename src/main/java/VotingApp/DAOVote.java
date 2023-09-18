package VotingApp;
import VotingApp.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.sql.Timestamp;

public class DAOVote {
    static final String PERSISTENCE_UNIT_NAME = "VotingApp";

    public void addVote(Vote vote) {
        try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
             EntityManager em = factory.createEntityManager()) {
            ;
        }
    }
}
