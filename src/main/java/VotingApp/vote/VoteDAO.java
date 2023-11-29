package VotingApp.vote;

import VotingApp.poll.Poll;
import VotingApp.user.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VoteDAO {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void addVote(Vote vote) {
        entityManager.persist(vote);
    }

    public Vote getVoteById(Long id) {
        return entityManager.find(Vote.class, id);
    }

    public List<Vote> getVotes() {
        return entityManager.createQuery("SELECT v FROM Vote v", Vote.class)
                .getResultList();
    }

    public List<Vote> getVotesByPoll(Poll poll) {
        return entityManager.createQuery("SELECT v FROM Vote v WHERE v.poll = :poll", Vote.class)
                .setParameter("poll", poll)
                .getResultList();
    }

    public List<Vote> getVotesByUser(User user) {
        return entityManager.createQuery("SELECT v FROM Vote v WHERE v.user = :user", Vote.class)
                .setParameter("user", user)
                .getResultList();
    }
    @Transactional
    public void deleteVoteById(Long id) {
        Vote vote = getVoteById(id);
        if (vote != null) {
            entityManager.remove(vote);
        }
    }

    @Transactional
    public void deleteVote(Vote vote) {
        entityManager.remove(vote);
    }

    @Transactional
    public void updateVote(Long id, Boolean choice) {
        Vote vote = getVoteById(id);
        vote.setChoice(choice);
        entityManager.merge(vote);
    }
}