package co.com.powerup.crediya.santiagomh04.msvcauthentication.exceptions.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthenticationErrorCauses {
    INVALID_CREDENTIALS_ERROR("either your email or password are invalid. Please, try again");

    private final String message;
}
