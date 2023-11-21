package VotingApp.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    LoginDAO loginDAO = new LoginDAO();

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String username , String password) {
        try {
            String login = loginDAO.verifyUser(username, password);
            if (login != null) {
                return ResponseEntity.ok(login);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }

    }
}
