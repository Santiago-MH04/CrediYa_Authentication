package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.routersRest;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.apiHandler.AuthenticationAPIHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class AuthenticationUserRest {

    private final AuthenticationAPIHandler authenticationHandler;

    @Bean
    public RouterFunction<ServerResponse> authRouterFunction() {
        return route(POST("/api/v1/login"), this.authenticationHandler::listenLoginUseCase);
    }
}
