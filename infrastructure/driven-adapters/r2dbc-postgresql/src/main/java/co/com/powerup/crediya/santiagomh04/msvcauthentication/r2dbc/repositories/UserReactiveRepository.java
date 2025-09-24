package co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.repositories;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.entities.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserReactiveRepository extends ReactiveCrudRepository<UserEntity, String> {
    Mono<UserEntity> findByIdentificationNumber(String identificationNumber);
    Mono<Boolean> existsByEmail(String email);
}
