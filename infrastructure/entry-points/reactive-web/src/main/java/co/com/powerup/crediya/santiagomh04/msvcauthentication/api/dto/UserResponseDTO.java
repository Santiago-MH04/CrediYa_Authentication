package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto;

import java.math.BigInteger;
import java.time.LocalDate;

public record UserResponseDTO(
    String id,
    String name,
    String lastName,
    String address,
    String telephone,
    String documentType,
    String identificationNumber,
    String email,
    LocalDate dateOfBirth,
    BigInteger baseSalary,
    RoleDTO role
) {
}
