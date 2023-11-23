package VotingApp.login;

import VotingApp.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    private LoginDAO loginDAO;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, Object> payload) {
        //System.out.println("Name: " + payload.get("name") + " Password: " + payload.get("password"));
        try {

            String login = loginDAO.verifyUser((String) payload.get("name"), (String) payload.get("password"));
            if (login != null) {
                return new ResponseEntity<>(login, HttpStatus.OK); //ResponseEntity.ok(login);
            } else {
                return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);//ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }

    }
}
