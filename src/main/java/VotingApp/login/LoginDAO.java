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

    public String verifyUser(String name, String password) throws Exception {
        String hashed = Hasher.toHexString(Hasher.getSHA(password));
        System.out.println("Name: " + name + " Password: " + password+ " Hashed: " + hashed);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("VotingApp");
        EntityManager em = emf.createEntityManager();


        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.name = :name AND u.password = :password", User.class);
        query.setParameter("name", name);
        query.setParameter("password", hashed);
        User user = query.getSingleResult();
        em.close();
        emf.close();
        Key key = new AesKey(ByteUtil.randomBytes(16));
        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setPayload(user.toString());
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        jwe.setKey(key);
        String serializedJwe = jwe.getCompactSerialization();
        if (hashed.equals(user.getPassword())) return serializedJwe;
        else return null;
    }


}
