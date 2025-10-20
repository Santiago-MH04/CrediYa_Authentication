package co.com.powerup.crediya.santiagomh04.msvcauthentication.jwtdrivenadapter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "authentication.security")
@Getter
@Setter
public class JwtTokenPreconfig {
    private String name;
    private String secretKey;
    private Long tokenValiditySeconds;
}
