package VotingApp;

import jakarta.persistence.EntityManager;

import java.util.List;

public class VoteDAO {

    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void addVote(Vote vote) {
        entityManager.persist(vote);
    }

    public Vote getVoteById(Long id) {
        return entityManager.find(Vote.class, id);
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