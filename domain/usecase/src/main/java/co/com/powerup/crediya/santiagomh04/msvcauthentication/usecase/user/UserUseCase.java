package co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.user;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.User;
import reactor.core.publisher.Mono;

public interface UserUseCase {
    Mono<User> createUser(User user);
    Mono<User> findByIdentificationNumber(String identificationNumber);
    Mono<User> findByEmail(String email);
    Mono<Boolean> existsByEmail(String email);
}
