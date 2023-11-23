package VotingApp.user;

import VotingApp.poll.Poll;
import VotingApp.security.Hasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDAO userDAO;



    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            user.setPassword(Hasher.toHexString(Hasher.getSHA(user.getPassword())));
            userDAO.addUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userDAO.getUsers();
            if (users != null) {
                return new ResponseEntity<>(users, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable String name) {
        try {
            User user = userDAO.getUserByUsername(name);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle or log the error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/user/poll/{userid}")
    public ResponseEntity<List<Poll>> getPollsByUser(@PathVariable Long userid){
        try{
            List<Poll> polls = userDAO.getPollsByUser(userid);
            if(polls != null){
                return new ResponseEntity<>(polls, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update/{name}")
    public ResponseEntity<String> updateUser(@PathVariable String name, @RequestBody User updatedUser, @RequestHeader User currentUser) {
        try {
            if (currentUser.getName().equals(name) || currentUser.getIsAdmin()) {
                userDAO.updateUser(updatedUser);
                return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteUser(@PathVariable String name, @RequestHeader User currentUser) {
        try {
            if (currentUser.getName().equals(name) || currentUser.getIsAdmin()) {
                userDAO.deleteUser(name);
                return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

