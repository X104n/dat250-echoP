package VotingApp.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;

import java.util.List;

@Service
public class UserDAO {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }

    public List<User> getUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
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
}
