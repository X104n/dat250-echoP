package VotingApp.login;
import VotingApp.security.Hasher;
import VotingApp.user.User;
import jakarta.persistence.*;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;

import java.security.Key;


public class LoginDAO {

    public String verifyUser(String email, String password) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("VotingApp");
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        User user = query.getSingleResult();
        em.close();
        emf.close();
        String hashed = Hasher.toHexString(Hasher.getSHA(password));
        Key key = new AesKey(ByteUtil.randomBytes(16));
        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setPayload(user.toString());
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        jwe.setKey(key);
        String serializedJwe = jwe.getCompactSerialization();
        if (hashed.equals(password)) return serializedJwe;
        else return null;
    }


}
