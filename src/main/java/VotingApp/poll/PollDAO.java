package VotingApp.poll;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PollDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void addPoll(Poll poll) {
        if (entityManager != null) {
            entityManager.persist(poll);
        } else {
        }
    }

    public Poll getPollById(Long pollId) {
        try {
            return entityManager.createQuery("SELECT p FROM Poll p WHERE p.id = :pollId", Poll.class)
                    .setParameter("pollId", pollId)
                    .getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }

    public List<Poll> getAllPolls() {
        try {
            return entityManager.createQuery("SELECT p FROM Poll p", Poll.class)
                    .getResultList();
        } catch(Exception e) {
            // Handle any exceptions, e.g., database connection issues
            return Collections.emptyList(); // Return an empty list in case of an error
        }
    }

    public void deletePollById(Long pollId) {
        try {
            // Find the Poll entity by its ID
            Poll poll = entityManager.find(Poll.class, pollId);

            if (poll != null) {
                // If the Poll exists, remove it from the database
                entityManager.remove(poll);
            }
            // No need to handle the case where the poll is not found since it's void
        } catch (Exception e) {
            // Handle any exceptions, e.g., database connection issues
        }
    }


}
