package VotingApp.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class JWS {
    private MacAlgorithm alg = Jwts.SIG.HS512; //or HS384 or HS256
    private SecretKey key = alg.key().build();

    public String sign(byte[] content) {
        return Jwts.builder().content(content, "text/plain").signWith(key, alg).compact();
    }

    public String verify(String token) {
        return new String(Jwts.parser().verifyWith(key).build().parseSignedContent(token).getPayload(), StandardCharsets.UTF_8);
    }
}
