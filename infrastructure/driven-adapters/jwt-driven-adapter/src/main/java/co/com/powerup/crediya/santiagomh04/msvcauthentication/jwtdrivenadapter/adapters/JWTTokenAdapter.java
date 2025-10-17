package co.com.powerup.crediya.santiagomh04.msvcauthentication.jwtdrivenadapter.adapters;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.jwtdrivenadapter.config.JwtTokenConfig;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.authentication.gateways.JwtRepository;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import static co.com.powerup.crediya.santiagomh04.msvcauthentication.jwtdrivenadapter.config.JwtTokenConfig.*;

@Component
public class JWTTokenAdapter implements JwtRepository {

    @Override
    public String generateToken(String email, String roleName) {

        long tokenValidityMillis = TOKEN_VALIDITY_SECONDS * 1000;

        return Jwts.builder()
            .subject(email)
            .issuer(name)   //Just in case of failure, change it to msvc-authentication
            .issuedAt(Date.from(Instant.now()))
            .expiration(new Date(System.currentTimeMillis() + tokenValidityMillis))
            .claim("email", email)
            .claim("role", roleName)
            .signWith(SECRET_KEY)
            .compact();
    }

    @Override
    public String extractUserEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    @Override
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    @Override
    public Boolean validateToken(String token) {
        /*try {*/
            Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token);

            Claims claims = claimsJws.getPayload();

            Date issuedAt = claims.getIssuedAt();
            Date now = new Date();

            return issuedAt != null && !issuedAt.after(now);
        /*} catch (ExpiredJwtException e) {
            *//*log.info("Token is expired");*//*
        } catch (UnsupportedJwtException e) {
            *//*log.info("Token not unsupported");*//*
        } catch (MalformedJwtException e) {
            *//*log.info("Malformed token");*//*
        } catch (SignatureException e) {
            *//*log.info("Invalid signature");*//*
        } catch (IllegalArgumentException e) {
            *//*log.info("Empty or null token");*//*
        }
        return false;*/
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(SECRET_KEY)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
