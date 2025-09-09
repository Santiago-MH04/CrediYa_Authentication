package co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.mapper;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.role.Role;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.role.gateways.RoleRepository;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.User;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ObjectMapper objectMapper;
    private final RoleRepository roleRepository;

    public Mono<User> toDomain(UserEntity userEntity){
        User user = this.objectMapper.map(userEntity, User.class);

        return this.roleRepository.findById(userEntity.getRoleId())
            .flatMap(roleEntity -> {
                Role role = objectMapper.map(roleEntity, Role.class);
                user.setRole(role);
                return Mono.just(user);
            });
    }

    public UserEntity toEntity(User user) {
        UserEntity userEntity = this.objectMapper.map(user, UserEntity.class);

        if (user.getRole() != null) {
            userEntity.setRoleId(user.getRole().getId());
        }

        return userEntity;
    }
}
