package co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.adapters;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.User;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.gateways.UserRepository;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.mappers.UserMapper;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.repositories.UserReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserReactiveRepositoryAdapter implements UserRepository {

    private final UserReactiveRepository repoUserReactive;
    private final UserMapper userMapper;

    @Override
    public Mono<User> save(User user) {
        return this.repoUserReactive.save(this.userMapper.toEntity(user))
            .flatMap(this.userMapper::toDomain);
    }

    @Override
    public Mono<User> findByIdentificationNumber(String identificationNumber) {
        return this.repoUserReactive.findByIdentificationNumber(identificationNumber)
            .flatMap(this.userMapper::toDomain);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return this.repoUserReactive.findByEmail(email)
            .flatMap(this.userMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return this.repoUserReactive.existsByEmail(email);
    }
}
