package VotingApp.login;
import VotingApp.security.Hasher;
import VotingApp.user.User;
import VotingApp.user.UserDAO;
import jakarta.persistence.*;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.List;


@Service
public class LoginDAO {

    @Autowired
    private UserDAO userDAO;

    public String verifyUser(String name, String password) throws Exception {
        String hashed = Hasher.toHexString(Hasher.getSHA(password));
        System.out.println("Name: " + name + " Password: " + password+ " Hashed: " + hashed);
        List<User> users = userDAO.getUsersByName(name);
        User theOne = null;
        for (User user : users) {
            if (hashed.equals(user.getPassword())) theOne = user;
        }

        Key key = new AesKey(ByteUtil.randomBytes(16));
        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setPayload(theOne.toString());
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        jwe.setKey(key);
        String serializedJwe = jwe.getCompactSerialization();
        if (theOne != null) {
            return serializedJwe;
        } else {
            return null;
        }
    }


}
