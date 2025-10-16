package co.com.powerup.crediya.santiagomh04.msvcauthentication.model.authentication.gateways;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.User;

public interface JwtRepository {

    String generateToken(String email, String roleName);

    String extractUserEmail(String token);

    String extractRole(String token);

    Boolean validateToken(String token);
}
