package VotingApp.poll;

import VotingApp.vote.Vote;
import VotingApp.mqtt.MqttService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Service
public class PollDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MqttService mqttService;

    @Transactional
    public void addPoll(Poll poll) {
        entityManager.persist(poll);
    }

    public Poll getPollById(Long pollId) {
        try {
            return entityManager.createQuery("SELECT p FROM Poll p WHERE p.id = :id", Poll.class)
                    .setParameter("id", pollId)
                    .getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }

    public List<Poll> getPolls() {
            return entityManager.createQuery("SELECT p FROM Poll p", Poll.class)
                    .getResultList();
    }

    @Transactional
    public void updatePoll(Long pollId, Poll updatedPoll) {
        try {
            Poll poll = entityManager.find(Poll.class, pollId);

            if (poll != null) {
                poll.setTitle(updatedPoll.getTitle());
                poll.setQuestion(updatedPoll.getQuestion());
                poll.setStartDate(updatedPoll.getStartDate());
                poll.setEndDate(updatedPoll.getEndDate());
                poll.setRequireLogin(updatedPoll.getRequireLogin());
                poll.setIsActive(updatedPoll.getIsActive());
                poll.setIsProcessed(updatedPoll.getIsProcessed());

                entityManager.merge(poll);
            }
        } catch (Exception e) {
            // Handle exceptions
        }
    }


    public List<Poll> getPollsToActivate() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        return entityManager.createQuery("SELECT p FROM Poll p WHERE p.startDate > :currentTimestamp OR p.endDate < :currentTimestamp", Poll.class)
                .setParameter("currentTimestamp", currentTimestamp)
                .getResultList();
    }

    public List<Poll> getPollsToEnd() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        return entityManager.createQuery("SELECT p FROM Poll p WHERE p.startDate <= :currentTimestamp AND p.endDate >= :currentTimestamp", Poll.class)
                .setParameter("currentTimestamp", currentTimestamp)
                .getResultList();
    }


    @Transactional
    public void deletePoll(Poll poll) {
        try {
            for (Vote vote : poll.getVotes()) {
                entityManager.remove(vote);
            }
            entityManager.remove(poll);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Transactional
    public void addGreenAndRedVotes(Vote vote){
        try{
            Poll poll = getPollById(vote.getPoll().getId());
            if(vote.getChoice().equals(Boolean.TRUE)){
                int greenVotes = poll.getGreenVotes();
                poll.setGreenVotes(greenVotes+1);
            }else if(vote.getChoice().equals(Boolean.FALSE)){
                int redVotes = poll.getRedVotes();
                poll.setRedVotes(redVotes+1);
            }
            entityManager.merge(poll);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Transactional
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

    @Transactional
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

    @Transactional
    public void deleteGreenAndRedVotes(Long id, Boolean choice){
        try{
            Poll poll = getPollById(id);
            if(choice){
                poll.setGreenVotes(poll.getGreenVotes()-1);
            }else if(choice.equals(Boolean.FALSE)){
                poll.setRedVotes(poll.getRedVotes()-1);
            }
            entityManager.merge(poll);
        }catch(Exception e){
            System.out.println(e);
        }
    }

}
