package VotingApp.security;

import VotingApp.user.User;
import VotingApp.user.UserDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(token);
            String name = jsonNode.get("name").asText();
            User user = userDAO.getUserByUsername(name);
            return user;
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
