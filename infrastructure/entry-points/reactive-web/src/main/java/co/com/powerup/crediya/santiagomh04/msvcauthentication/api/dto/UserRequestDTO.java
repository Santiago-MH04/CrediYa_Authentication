package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto;

import java.math.BigInteger;
import java.time.LocalDate;

public record UserRequestDTO(
    String name,
    String lastName,
    String address,
    String telephone,
    LocalDate dateOfBirth,
    BigInteger baseSalary,
    String email,
    String password,
    String documentType,
    String identificationNumber
) {
}
