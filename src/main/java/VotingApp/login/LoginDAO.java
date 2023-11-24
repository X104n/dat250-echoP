package VotingApp.login;
import VotingApp.security.Hasher;
import VotingApp.user.User;
import VotingApp.user.UserDAO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;

import static io.jsonwebtoken.Jwts.SIG.HS256;


@Service
public class LoginDAO {

    @Autowired
    private UserDAO userDAO;

    public String verifyUser(String name, String password) throws Exception {
        String hashed = Hasher.toHexString(Hasher.getSHA(password));
        List<User> users = userDAO.getUsersByName(name);
        User theOne = null;
        for (User user : users) {
            if (hashed.equals(user.getPassword())) theOne = user;
        }
        MacAlgorithm alg = Jwts.SIG.HS512; //or HS384 or HS256
        SecretKey key = alg.key().build();
        byte[] content = userToJson(theOne).getBytes(StandardCharsets.UTF_8);

// Create the compact JWS:
        String jws = Jwts.builder().content(content, "text/plain").signWith(key, alg).compact();

        System.out.println("Serialized Encrypted JWE: " + jws);
        if (theOne != null) {
            return jws;
        } else {
            return null;
        }
    }
    private String userToJson(User user) {
        return "{" +
                "\"userId\":" + user.getUserID() + "," +
                "\"name\":\"" + user.getName() + "\"," +
                "\"email\":\"" + user.getEmail() + "\"," +
                "\"password\":\"" + user.getPassword() + "\"," +
                "\"isAdmin\":" + user.getIsAdmin() +
                "}";
    }

}
