package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
    LocalDateTime timestamp,
    String path,
    String message,
    String status
) {
}
