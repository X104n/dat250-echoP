package VotingApp;

import jakarta.persistence.EntityManager;

public class UserDAO {

    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void addUser(User user) {
        if (entityManager != null) {
            entityManager.persist(user);
        } else {
            // Handle error: EntityManager not set.
        }
    }

    public User getUserByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.name = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch(Exception e) {
            // Handle or log the error.
            return null;
        }
    }
}

