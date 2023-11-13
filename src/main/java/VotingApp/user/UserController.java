package VotingApp.user;

import VotingApp.poll.Poll;
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

    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        try {
            User user = userDAO.getUserByUsername(username);
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
    @PutMapping("/update/{username}")
    public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody User updatedUser, @CurrentUser User currentUser) {
        try {
            if (currentUser.getName().equals(username) || currentUser.getIsAdmin()) {
                userDAO.updateUser(updatedUser);
                return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username, @CurrentUser User currentUser) {
        try {
            if (currentUser.getName().equals(username) || currentUser.getIsAdmin()) {
                userDAO.deleteUser(username);
                return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

