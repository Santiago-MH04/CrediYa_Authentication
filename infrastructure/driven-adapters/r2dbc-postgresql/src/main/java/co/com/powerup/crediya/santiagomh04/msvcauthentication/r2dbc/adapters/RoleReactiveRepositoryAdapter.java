package co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.adapters;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.role.Role;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.role.gateways.RoleRepository;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.mappers.RoleMapper;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.repositories.RoleReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class RoleReactiveRepositoryAdapter implements RoleRepository {

    private final RoleReactiveRepository repoRoleReactive;
    private final RoleMapper roleMapper;

    @Override
    public Mono<Role> findById(Long id) {
        return this.repoRoleReactive.findById(id)
            .map(this.roleMapper::toDomain);
    }

    @Override
    public Mono<Role> findByName(String name) {
        return this.repoRoleReactive.findByName(name)
            .map(this.roleMapper::toDomain);
    }

    @Override
    public Flux<Role> findAll() {
        return this.repoRoleReactive.findAll()
            .map(this.roleMapper::toDomain);
    }
}
