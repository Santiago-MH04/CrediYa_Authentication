package co.com.powerup.crediya.santiagomh04.msvcauthentication.model.authentication;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserCredentials {

    private String email;

    private String password;
}
