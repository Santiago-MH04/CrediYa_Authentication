package co.com.powerup.crediya.santiagomh04.msvcauthentication.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String email;
    private final String token;

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String email, String token) {
        super(authorities);
        this.setAuthenticated(true);

        this.email = email;
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.email;
    }
}
