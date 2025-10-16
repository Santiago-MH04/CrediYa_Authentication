package co.com.powerup.crediya.santiagomh04.msvcauthentication.exceptions.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenValidationCauses {
    TOKEN_EXPIRATION_ERROR("the token validity period has expired"),
    TOKEN_UNSUPPORTED_OPERATION_ERROR("unsupported token"),
    MALFORMED_TOKEN_ERROR("the current token has been adultered or malformed"),
    TOKEN_SIGNATURE_ERROR("invalid token signature");

    private final String message;
}
