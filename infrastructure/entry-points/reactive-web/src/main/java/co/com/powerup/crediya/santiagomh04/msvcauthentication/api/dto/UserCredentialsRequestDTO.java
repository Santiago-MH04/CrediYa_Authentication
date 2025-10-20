package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UserCredentialsRequestDTO(
    @NotBlank(message = "the email is mandatory")
    String email,

    @NotBlank(message = "the password is mandatory")
    String password
) {
}
