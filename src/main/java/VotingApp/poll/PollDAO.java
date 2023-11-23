package VotingApp.poll;

import VotingApp.vote.Vote;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void addPoll(Poll poll) {
        entityManager.persist(poll);
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

    public List<Poll> getPolls() {
            return entityManager.createQuery("SELECT p FROM Poll p", Poll.class)
                    .getResultList();
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
    public void addGreenAndRedVotes(Vote vote){
        try{
            Poll poll = vote.getPoll();
            if(vote.getChoice().equals(Boolean.TRUE)){
                poll.setGreenVotes(poll.getGreenVotes()+1);
            }else if(vote.getChoice().equals(Boolean.FALSE)){
                poll.setRedVotes(poll.getRedVotes()+1);
            }
            entityManager.merge(poll);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void deleteGreenAndRedVotes(Vote vote){
        try{
            Poll poll = vote.getPoll();
            if(vote.getChoice().equals(Boolean.TRUE)){
                poll.setGreenVotes(poll.getGreenVotes()-1);
            }else if(vote.getChoice().equals(Boolean.FALSE)){
                poll.setRedVotes(poll.getRedVotes()-1);
            }
            entityManager.merge(poll);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void addGreenAndRedVotes(Long id, Boolean choice){
        try{
            Poll poll = getPollById(id);
            if(choice){
                poll.setGreenVotes(poll.getGreenVotes()+1);
            }else if(choice.equals(Boolean.FALSE)){
                poll.setRedVotes(poll.getRedVotes()+1);
            }
            entityManager.merge(poll);
        }catch(Exception e){
            System.out.println(e);
        }
    }

}
