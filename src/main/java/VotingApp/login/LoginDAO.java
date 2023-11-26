package VotingApp.login;
import VotingApp.poll.Poll;
import VotingApp.security.Hasher;
import VotingApp.security.JWS;
import VotingApp.user.User;
import VotingApp.user.UserDAO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import static io.jsonwebtoken.Jwts.SIG.HS256;


@Service
public class LoginDAO {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JWS jws;


    public String verifyUser(String name, String password) throws Exception {
        String hashed = Hasher.toHexString(Hasher.getSHA(password));
        List<User> users = userDAO.getUsersByName(name);
        User theOne = null;
        for (User user : users) {
            if (hashed.equals(user.getPassword())) theOne = user;
        }

        byte[] content = userToJson(theOne).getBytes(StandardCharsets.UTF_8);

// Create the compact JWS:
        String jwsString = jws.sign(content);

        System.out.println("content: " + content);
        System.out.println("Serialized JWS: " + jwsString);
        System.out.println("Decrypted JWE: " + jws.verify(jwsString));
        //System.out.println("verifiedUser: " + ByteArrayToString(jws.verify(jwsString)));
        if (theOne != null) {
            return jwsString;
        } else {
            return null;
        }
    }
    private String userToJson(User user) {
        List<Long> pollIds = new ArrayList<>();
        for (Poll poll : user.getPolls()) {
            pollIds.add(poll.getId());
        }
        return "{" +
                "\"userId\":" + user.getUserID() + "," +
                "\"name\":\"" + user.getName() + "\"," +
                "\"email\":\"" + user.getEmail() + "\"," +
                "\"password\":\"" + user.getPassword() + "\"," +
                "\"isAdmin\":" + user.getIsAdmin() + "," +
                "\"polls\":" + pollIds +
                "}";
    }
    private String ByteArrayToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

    }

}
