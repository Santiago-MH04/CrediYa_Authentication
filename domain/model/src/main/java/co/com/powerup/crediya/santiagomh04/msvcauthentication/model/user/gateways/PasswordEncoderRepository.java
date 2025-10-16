package co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.gateways;

public interface PasswordEncoderRepository {

    String hashPassword(String password);

    Boolean matchesPassword(String plainPassword, String hashedPassword);
}
