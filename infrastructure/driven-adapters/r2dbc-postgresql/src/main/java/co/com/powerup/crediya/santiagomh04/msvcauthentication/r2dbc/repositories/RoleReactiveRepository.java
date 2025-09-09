package co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.repositories;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.entities.RoleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleReactiveRepository extends ReactiveCrudRepository<RoleEntity, Long> {
    Mono<RoleEntity> findByName(String name);
}
