package co.com.powerup.crediya.santiagomh04.msvcauthentication.jwtdrivenadapter.config;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@RequiredArgsConstructor
public class JwtTokenConfig {

    private final JwtTokenPreconfig jwtTokenPreconfig;

    @Bean
    public String tokenIssuer(){
        return this.jwtTokenPreconfig.getName();
    }

    @Bean
    public SecretKey jwtSecretKey() {
        return Keys.hmacShaKeyFor(
            java.util.Base64.getDecoder().decode(this.jwtTokenPreconfig.getSecretKey())
        );
    }

    @Bean
    public Long tokenValiditySeconds() {
        return this.jwtTokenPreconfig.getTokenValiditySeconds();
    }
}
