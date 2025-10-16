package co.com.powerup.crediya.santiagomh04.msvcauthentication.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtPreauthenticationToken extends AbstractAuthenticationToken {

    private final String token;

    public JwtPreauthenticationToken(String token) {
        super(null);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
