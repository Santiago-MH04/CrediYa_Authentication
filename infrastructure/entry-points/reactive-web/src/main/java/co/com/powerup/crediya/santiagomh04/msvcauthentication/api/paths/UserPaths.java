package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.paths;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "routes.paths")
public class UserPaths {
    private String users;
    private String userById;
    private String userByIdentificationNumber;
}
