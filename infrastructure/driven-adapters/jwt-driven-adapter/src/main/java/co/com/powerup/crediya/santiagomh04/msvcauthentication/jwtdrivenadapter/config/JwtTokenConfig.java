package co.com.powerup.crediya.santiagomh04.msvcauthentication.jwtdrivenadapter.config;

import io.jsonwebtoken.Jwts;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.crypto.SecretKey;

@ConfigurationProperties(prefix = "spring.application")
public class JwtTokenConfig {
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final Long TOKEN_VALIDITY_SECONDS = 2_592_000L;
    public static String name;


    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORISATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
}
