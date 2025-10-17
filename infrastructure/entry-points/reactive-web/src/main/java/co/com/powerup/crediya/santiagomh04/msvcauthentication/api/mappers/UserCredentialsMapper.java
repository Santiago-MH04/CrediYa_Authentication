package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.mappers;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.AccessTokenResponseDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.UserCredentialsRequestDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.UserRequestDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.UserResponseDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.authentication.AccessToken;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.authentication.UserCredentials;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserCredentialsMapper {

    UserCredentials toDomain(UserCredentialsRequestDTO userCredentialsRequestDTO);

    AccessTokenResponseDTO toResponse(AccessToken accessToken);
}
