package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.mappers;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.UserRequestDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.UserResponseDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {RoleApiMapper.class})
public interface UserApiMapper {
    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "role", ignore = true)
    })
    User toDomain(UserRequestDTO userRequestDTO);

    UserResponseDTO toResponse(User user);
}
