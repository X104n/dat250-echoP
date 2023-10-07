package VotingApp.user;

import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public void addUser(User user) {
        if (entityManager != null) {
            entityManager.persist(user);
        } else {
        }
    }

    public User getUserByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.name = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }
    public void setEntityManager(EntityManager em) {
    }
}
