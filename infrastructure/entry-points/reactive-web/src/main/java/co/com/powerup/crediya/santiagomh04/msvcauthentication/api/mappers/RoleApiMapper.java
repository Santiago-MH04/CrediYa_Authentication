package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.mappers;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.RoleDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.role.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/*@Mapper*/ //Implementing the static injection, it no longer becomes a component in the Spring context
/*public abstract class RoleApiMapper {*/
@Mapper(componentModel = "spring")
public interface RoleApiMapper {

    /*public static final RoleApiMapper INSTANCE = Mappers.getMapper(RoleApiMapper.class);*/

    public abstract RoleDTO toResponse(Role role);
}
