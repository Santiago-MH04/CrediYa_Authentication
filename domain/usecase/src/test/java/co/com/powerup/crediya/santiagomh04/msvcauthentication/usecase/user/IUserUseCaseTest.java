package co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.user;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.exceptions.business.BusinessException;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.exceptions.validation.ValidationException;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.role.Role;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.role.gateways.RoleRepository;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.User;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.gateways.UserRepository;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.user.validations.UserValidator;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class IUserUseCaseTest {
    @Mock
    private UserRepository repoUser;

    @Mock
    private RoleRepository repoRole;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private IUserUseCase userUseCase;

    private User user;
    private Role role;

    @BeforeEach
    void setup() {
        this.user = User.builder()
            .name("Pepito")
            .lastName("Pérez")
            .address("Calle de prueba 123")
            .telephone("1234567890")
            .dateOfBirth(LocalDate.of(1990, 5, 15))
            .baseSalary(new BigInteger("2000000"))
            .identificationNumber("10101010")
            .email("pepito.perez@correo.com")
            .password("Password123!")
            .build();

        this.role = Role.builder()
            .id(1L)
            .name(Role.RoleName.ROLE_APPLICANT.name())
            .build();
    }

    @Test
    @DisplayName("It should register a user if all validations are successful")
    void shouldCreateUserWhenAllValidationsAreSuccessful() {
        // 1. Configure the mocks for the validations to pass
        when(this.userValidator.validateUser(any(User.class))).thenReturn(Mono.empty());

        // 2. Configure the mocks for the validations to pass
        when(this.repoRole.findByName(any(String.class))).thenReturn(Mono.just(this.role));
        when(this.repoUser.save(any(User.class))).thenReturn(Mono.just(this.user));

        // 3. Run the method to test and verify the result
        StepVerifier.create(this.userUseCase.createUser(this.user))
            .expectNextMatches(createdUser -> createdUser.getEmail().equals(this.user.getEmail()))
            .verifyComplete();
    }

    @Test
    @DisplayName("Should throw error if the validation fails")
    void shouldReturnErrorWhenValidationFails() {
        // 1. Configure the mock for the validation to fail
        when(this.userValidator.validateUser(any(User.class)))
            .thenReturn(Mono.error(new ValidationException("Validation error simulated.")));

        // 2. Run the method to test
        StepVerifier.create(userUseCase.createUser(user))
            .verifyErrorMatches(throwable ->
                throwable instanceof ValidationException &&
                    throwable.getMessage().equals("Validation error simulated.")
            );
    }

    @Test
    @DisplayName("Should throw error if the default role is not found")
    void shouldReturnErrorIfDefaultRoleIsNotFound() {
        // 1. Configure mocks
        when(this.userValidator.validateUser(any(User.class))).thenReturn(Mono.empty());
        when(this.repoRole.findByName(any(String.class))).thenReturn(Mono.empty());

        // 2. Run the method to test and veirfy the error
        StepVerifier.create(this.userUseCase.createUser(this.user))
            .verifyError(); // Only verify there’s an error at this stage, the exact error message would be handled by a different layer
    }

    @Test
    @DisplayName("Should return the user when searching by a valid identification number")
    void ShouldReturnUserWhenItExistsSearchingByIdentificationNumber() {
        // 1. Arrange
        when(this.userValidator.validateUserSearch("10101010")).thenReturn(Mono.just(this.user));

        // 2. Act & Assert
        StepVerifier.create(this.userUseCase.findByIdentificationNumber("10101010"))
            .expectNext(this.user)
            .verifyComplete();

        verify(this.userValidator).validateUserSearch("10101010");
    }

    @Test
    @DisplayName("Should throw error if the identification number provided don’t match to any user registered")
    void ShouldThrowErrorWhenUserNotFoundSearchingByIdentificationNumber() {
        // 1. Arrange
        when(this.userValidator.validateUserSearch("999"))
            .thenReturn(Mono.error(new BusinessException("User not found")));

        // 2. Act & Assert
        StepVerifier.create(this.userUseCase.findByIdentificationNumber("999"))
            .expectErrorMatches(ex -> ex instanceof BusinessException &&
                ex.getMessage().contains("User not found")
            )
            .verify();

        verify(this.userValidator).validateUserSearch("999");
    }
}