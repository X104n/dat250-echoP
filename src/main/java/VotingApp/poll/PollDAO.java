package VotingApp.poll;

import jakarta.persistence.EntityManager;

public class PollDAO {

    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void addPoll(Poll poll) {
        entityManager.persist(poll);
    }

    public Poll getPollById(Long id) {
        return entityManager.find(Poll.class, id);

    }
}
