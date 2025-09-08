package co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.user.validations;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.exceptions.validation.ValidationException;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.User;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {
    @Mock
    private UserRepository repoUser;

    @InjectMocks
    private UserValidator userValidator;

    private User user;

    @BeforeEach
    void setup() {
        // Initialises a valid User object before each test
        user = User.builder()
            .name("Pepito")
            .lastName("Pérez")
            .address("Calle de la alegría 240")
            .telephone("1234567890")
            .dateOfBirth(LocalDate.of(1990, 5, 15))
            .baseSalary(new BigInteger("2000000"))
            .identificationNumber("10101010")
            .email("pepito.perez@correo.com")
            .password("Password123!")
            .build();
    }

    @Test
    @DisplayName("Should fail the field validation if 'name' is null")
    void shouldFailIfNameIsNull() {
        // 1. Set the scenario: make the field 'name' sea null.
        this.user.setName(null);

        // 2. Run the validation and verify the result.
        StepVerifier.create(this.userValidator.validateUser(this.user))
            .verifyErrorMatches(throwable ->
                throwable instanceof ValidationException &&
                    throwable.getMessage().contains("null")
            );
    }

    @Test
    @DisplayName("Should throw error if the email is already registered")
    void shouldFailWhenEmailIsRepeated() {
        // 1. Mocking the repository to simulate an existing email.
        when(this.repoUser.existsByEmail(this.user.getEmail()))
                .thenReturn(Mono.just(true));

        // 2. Run the validation and verify the result.
        StepVerifier.create(this.userValidator.validateUser(this.user))
            .verifyErrorMatches(throwable ->
                throwable instanceof ValidationException &&
                    throwable.getMessage().contains("the email you use is already registered")
            );
    }

    @Test
    @DisplayName("Should throw error if the email format is invalid")
    void shouldFailIfEmailFormatIsInvalid() {
        // 1. Set an email incorrectly formatted
        this.user.setEmail("email.invalid");

        // 2. Run the validation and verify the result.
        StepVerifier.create(this.userValidator.validateUser(this.user))
            .verifyErrorMatches(throwable ->
                throwable instanceof ValidationException &&
                    throwable.getMessage().contains("e.g. name@example.com")
            );
    }

    @Test
    @DisplayName("Should throw error if the password format is invalid")
    void shouldFailIfPasswordFormatIsInvalid() {
        // 1. Set a password incorrectly formatted
        this.user.setPassword("password123!");

        // 2. Run the validation and verify the result.
        StepVerifier.create(this.userValidator.validateUser(this.user))
            .verifyErrorMatches(throwable ->
                throwable instanceof ValidationException &&
                    throwable.getMessage().contains("at least 8 characters, one upper case, one lower case")
            );
    }

    @Test
    @DisplayName("Should throw error if the telephone format is invalid")
    void shouldFailIfTelephoneFormatIsInvalid() {
        // 1. Set a telephone incorrectly formatted
        this.user.setTelephone("+1234567890");

        // 2. Run the validation and verify the result.
        StepVerifier.create(this.userValidator.validateUser(this.user))
            .verifyErrorMatches(throwable ->
                throwable instanceof ValidationException &&
                    throwable.getMessage().contains("your telephone number must be entirely numeric")
            );
    }

    @Test
    @DisplayName("Should throw error if the user’s age is shorter than 18 years old")
    void shouldFailIfUnderageUser() {
        // 1. Set an underage user’s age
        this.user.setDateOfBirth(LocalDate.now());

        // 2. Run the validation and verify the result.
        StepVerifier.create(this.userValidator.validateUser(this.user))
            .verifyErrorMatches(throwable ->
                throwable instanceof ValidationException &&
                        throwable.getMessage().contains("must be at least 18 years old")
            );
    }

    @Test
    @DisplayName("Should throw error if the user’s base salary is out of range [0, 15000000]")
    void shouldFailIfBaseSalaryOutOfRange() {
        // 1. Set an underage user’s age
        this.user.setBaseSalary(new BigInteger("-2"));

        // 2. Run the validation and verify the result.
        StepVerifier.create(this.userValidator.validateUser(this.user))
            .verifyErrorMatches(throwable ->
                throwable instanceof ValidationException &&
                    throwable.getMessage().contains("must be within the range 0 to 15 millions")
            );
    }
}