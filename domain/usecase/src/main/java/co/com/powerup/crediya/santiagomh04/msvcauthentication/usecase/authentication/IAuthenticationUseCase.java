package co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.authentication;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.exceptions.authentication.AuthenticationErrorCauses;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.exceptions.authentication.AuthenticationException;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.authentication.AccessToken;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.authentication.UserCredentials;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.authentication.gateways.JwtRepository;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.gateways.PasswordEncoderRepository;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class IAuthenticationUseCase implements AuthenticationUseCase{

    private final UserRepository repoUser;
    private final PasswordEncoderRepository passwordEncoder;
    private final JwtRepository repoJwt;

    @Override
    public Mono<AccessToken> login(UserCredentials userCredentials) {

        return this.repoUser.findByEmail(userCredentials.getEmail())
                .switchIfEmpty(Mono.error(new AuthenticationException(AuthenticationErrorCauses.INVALID_CREDENTIALS_ERROR.getMessage())))
            .filter(user -> this.passwordEncoder.matchesPassword(userCredentials.getPassword(), user.getPassword()))
                .switchIfEmpty(Mono.error(new AuthenticationException(AuthenticationErrorCauses.INVALID_CREDENTIALS_ERROR.getMessage())))
            .map(user -> {
                String accessToken = this.repoJwt.generateToken(user.getEmail(), user.getRole().getName());
                return new AccessToken(accessToken);
            });
    }
}

