package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Microservice users authentication")
                .version("v1.0")
                .description("User creation microservice with centralised logging and reactive handler support")

            );
    }
}
