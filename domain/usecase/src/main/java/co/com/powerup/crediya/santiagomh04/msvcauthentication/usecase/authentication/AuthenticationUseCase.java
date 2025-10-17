package co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.authentication;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.authentication.AccessToken;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.authentication.UserCredentials;
import reactor.core.publisher.Mono;

public interface AuthenticationUseCase {
    public Mono<AccessToken> login(UserCredentials userCredentials);
}
