package co.com.powerup.crediya.santiagomh04.msvcauthentication.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.user.validations",
    includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UserValidator$")
    },
    useDefaultFilters = false
)
public class UserValidatorConfig {
}
