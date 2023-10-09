package VotingApp.poll;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    public void updatePoll(Long pollId, Poll updatedPoll) {
        try {
            // Find the Poll entity by its ID
            Poll poll = entityManager.find(Poll.class, pollId);

            if (poll != null) {
                // Update the properties using the updatedPoll object
                poll.setTitle(updatedPoll.getTitle());
                poll.setQuestion(updatedPoll.getQuestion());
                poll.setStartDateTime(updatedPoll.getStartDateTime());
                poll.setEndDateTime(updatedPoll.getEndDateTime());
                poll.setIsPublic(updatedPoll.getIsPublic());

                // Persist the changes to the database
                entityManager.merge(poll);
            }
            // You can choose to handle the case where the Poll is not found,
            // but in a void method, you may decide not to take any specific action.
        } catch (Exception e) {
            // Handle any exceptions, e.g., database connection issues, or log them
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
