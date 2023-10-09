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

    public List<Vote> getAllVotes() {
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
}