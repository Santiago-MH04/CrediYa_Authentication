package co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.gateways;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> save(User user);
    Mono<User> findByIdentificationNumber(String identificationNumber);
    Mono<User> findByEmail(String email);
    Mono<Boolean> existsByEmail(String email);
}



