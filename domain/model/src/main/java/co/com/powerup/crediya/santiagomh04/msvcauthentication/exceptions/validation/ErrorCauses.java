package co.com.powerup.crediya.santiagomh04.msvcauthentication.exceptions.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCauses {
    EMPTY_FIELD_ERROR("these fields cannot be empty, nor null"),
    REPEATED_EMAIL_ERROR("the email you use is already registered"),
    INVALID_EMAIL_FORMAT_ERROR("you must use a valid email format (e.g. name@example.com)"),
    INVALID_PASSWORD_FORMAT_ERROR("your password must contain at least 8 characters, one upper case, one lower case, one number and one special character"),
    INVALID_TELEPHONE_FORMAT_ERROR("your telephone number must be entirely numeric and contain 10 digits"),
    UNDERAGE_ERROR("you must be at least 18 years old to get an account"),
    SALARY_OUT_OF_RANGE_ERROR("your salary must be within the range 0 to 15 millions");

    private final String message;
}
