package co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.mapper;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.role.Role;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.entities.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final ObjectMapper objectMapper;

    public Role toDomain(RoleEntity roleEntity) {
        return this.objectMapper.map(roleEntity, Role.class);
    }

    public RoleEntity toEntity(Role role) {
        return this.objectMapper.map(role, RoleEntity.class);
    }

}
