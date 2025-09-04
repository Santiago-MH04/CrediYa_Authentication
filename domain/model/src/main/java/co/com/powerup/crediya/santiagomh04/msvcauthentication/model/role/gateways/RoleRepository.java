package co.com.powerup.crediya.santiagomh04.msvcauthentication.model.role.gateways;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.role.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleRepository {
    Mono<Role> findByName(String name);
    Flux<Role> findAll();
}
