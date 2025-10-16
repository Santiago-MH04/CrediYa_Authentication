package co.com.powerup.crediya.santiagomh04.msvcauthentication.security;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.authentication.gateways.JwtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtRepository repoJwt;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();

        if (!this.repoJwt.validateToken(token)) {
            return Mono.empty();
        }

        String email = this.repoJwt.extractUserEmail(token);
        String role = this.repoJwt.extractRole(token);

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        AbstractAuthenticationToken auth = new JwtAuthenticationToken(authorities,email, token);
        return Mono.just(auth);
    }
}
