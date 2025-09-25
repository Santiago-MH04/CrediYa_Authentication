package co.com.powerup.crediya.santiagomh04.msvcauthentication.exceptions.business;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InternalCauses {
    USER_NOT_FOUND("the user you’re searching is not registered in our system");

    private final String message;
}
