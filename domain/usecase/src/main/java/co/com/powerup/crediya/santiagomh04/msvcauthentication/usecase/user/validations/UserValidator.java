package co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.user.validations;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.exceptions.business.BusinessException;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.exceptions.business.InternalCauses;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.exceptions.validation.ErrorCauses;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.exceptions.validation.ValidationException;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.User;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class UserValidator {
    private static final String EMAIL_FORMAT_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
    private static final String PASSWORD_FORMAT_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$";
    private static final String TELEPHONE_FORMAT_REGEX = "\\d{10}";

    private static final BigInteger LOWER_BOUND = new BigInteger("0");
    private static final BigInteger UPPER_BOUND = new BigInteger("15000000");

    private final UserRepository repoUser;

    //Non blocking validations
    public Mono<Void> validateUser(User user) {
        return validateRequiredFields(user)
            .then(Mono.defer(() -> validateEmailUniqueness(user.getEmail())))
            .then(Mono.defer(() -> validateEmailFormat(user.getEmail())))
            .then(Mono.defer(() -> validatePasswordFormat(user.getPassword())))
            .then(Mono.defer(() -> validateTelephoneFormat(user.getTelephone())))
            .then(Mono.defer(() -> validateAge(user.getDateOfBirth())))
            .then(Mono.defer(() -> validateSalaryRange(user.getBaseSalary())));
    }

    public Mono<User> validateUserSearch(String identificationNumber) {
        return this.repoUser.findByIdentificationNumber(identificationNumber)
            .switchIfEmpty(Mono.error(new BusinessException(InternalCauses.USER_NOT_FOUND.getMessage())));
    }


    //1. Required fields validation
    private Mono<Void> validateRequiredFields(User user) {
        String missingFields = Stream.of(
                getMissingFieldName(user.getName(), "name"),
                getMissingFieldName(user.getLastName(), "lastName"),
                getMissingFieldName(user.getAddress(), "address"),
                getMissingFieldName(user.getTelephone(), "telephone"),
                getMissingFieldName(user.getDateOfBirth(), "dateOfBirth"),
                getMissingFieldName(user.getBaseSalary(), "baseSalary"),
                getMissingFieldName(user.getDocumentType(), "documentType"),
                getMissingFieldName(user.getIdentificationNumber(), "identificationNumber"),
                getMissingFieldName(user.getEmail(), "email"),
                getMissingFieldName(user.getPassword(), "password")
            )
            .filter(Objects::nonNull)
            .collect(Collectors.joining(", "));

        if (!missingFields.isEmpty()) {
            String errorMessage = String.format("(%s): %s", missingFields, ErrorCauses.EMPTY_FIELD_ERROR.getMessage());
            return Mono.error(new ValidationException(errorMessage));
        }

        return Mono.empty();
    }

    private String getMissingFieldName(Object value, String fieldName) {
        return (value == null || (value instanceof String && ((String) value).isBlank())) ? fieldName : null;
    }

    //2. Unique email validation
    private Mono<Void> validateEmailUniqueness(String email) {
        return this.repoUser.existsByEmail(email)
            .flatMap(exists -> {
                if (Boolean.TRUE.equals(exists)) {
                    return Mono.error(new ValidationException(ErrorCauses.REPEATED_EMAIL_ERROR.getMessage()));
                }
                return Mono.empty();
            });
    }

    //3. Email format validation
    private Mono<Void> validateEmailFormat(String email) {
        if (!email.matches(EMAIL_FORMAT_REGEX)) {
            return Mono.error(new ValidationException(ErrorCauses.INVALID_EMAIL_FORMAT_ERROR.getMessage()));
        }
        return Mono.empty();
    }

    //4. Password format validation
    private Mono<Void> validatePasswordFormat(String password) {
        if (!password.matches(PASSWORD_FORMAT_REGEX)) {
            return Mono.error(new ValidationException(ErrorCauses.INVALID_PASSWORD_FORMAT_ERROR.getMessage()));
        }
        return Mono.empty();
    }

    //5. Telephone format validation
    private Mono<Void> validateTelephoneFormat(String telephone) {
        if (!telephone.matches(TELEPHONE_FORMAT_REGEX)) {
            return Mono.error(new ValidationException(ErrorCauses.INVALID_TELEPHONE_FORMAT_ERROR.getMessage()));
        }
        return Mono.empty();
    }

    //6. Age validation
    private Mono<Void> validateAge(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        int age = Period.between(dateOfBirth, today).getYears();

        if (age < 18) {
            return Mono.error(new ValidationException(ErrorCauses.UNDERAGE_ERROR.getMessage()));
        }
        return Mono.empty();
    }

    //7. Salary range validation
    private Mono<Void> validateSalaryRange(BigInteger baseSalary) {
        if (baseSalary.compareTo(LOWER_BOUND) < 0 || baseSalary.compareTo(UPPER_BOUND) > 0) {
            return Mono.error(new ValidationException(ErrorCauses.SALARY_OUT_OF_RANGE_ERROR.getMessage()));
        }
        return Mono.empty();
    }
}
