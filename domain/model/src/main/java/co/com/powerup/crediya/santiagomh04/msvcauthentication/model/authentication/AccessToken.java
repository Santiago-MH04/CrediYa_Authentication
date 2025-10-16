package co.com.powerup.crediya.santiagomh04.msvcauthentication.model.authentication;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AccessToken {
    
    private String token;
}
