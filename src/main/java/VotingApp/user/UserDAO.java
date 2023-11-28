package VotingApp.user;

import VotingApp.poll.Poll;
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
    public User getUserByUsername(String name) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }
    public List<User> getUsersByName(String name) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class)
                    .setParameter("name", name)
                    .getResultList();
        } catch(Exception e) {
            return null;
        }
    }
    public List<Poll> getPollsByUser(User user){
        try{
            Long userid = user.getUserID();
            return entityManager.createQuery("SELECT p FROM Poll p WHERE p.createdBy = :userid", Poll.class)
                    .setParameter("userid", userid)
                    .getResultList();
        }catch(Exception e){
            return null;
        }
    }

    @Transactional
    public void updateUser(User user) {
        if (entityManager != null) {
            entityManager.merge(user);
        }
    }
    @Transactional
    public void deleteUser(String name) throws Exception {
        User user = getUserByUsername(name);
        if (user != null) {
            entityManager.remove(user);
        } else {
            throw new Exception("User not found");
        }
    }

}
