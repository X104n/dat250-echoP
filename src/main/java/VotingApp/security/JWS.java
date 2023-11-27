package VotingApp.security;

import VotingApp.user.User;
import VotingApp.user.UserDAO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class JWS {

    @Autowired
    private UserDAO userDAO;

    private MacAlgorithm alg = Jwts.SIG.HS512; //or HS384 or HS256
    private SecretKey key = alg.key().build();

    public String sign(byte[] content) {
        return Jwts.builder().content(content, "text/plain").signWith(key, alg).compact();
    }

    public String verify(String token) {
        return new String(Jwts.parser().verifyWith(key).build().parseSignedContent(token).getPayload(), StandardCharsets.UTF_8);
    }

    public User getUserFromToken(String token) {
        if(token == null || token.equals("")) return null;
        token = verify(token);
        JsonObject jsonObject = new JsonParser().parse(token).getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        User user = userDAO.getUserByUsername(name);
        return user;
    }
}
