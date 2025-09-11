package co.com.powerup.crediya.santiagomh04.msvcauthentication.api;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.paths.UserPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    private final UserPaths userPaths;
    private final Handler userHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(/*Handler handler*/) {
        return route(POST(this.userPaths.getUsers()), this.userHandler::listenPOSTUseCase);

        /*return route(GET("/api/usecase/path"), handler::listenGETUseCase)
            .andRoute(POST("/api/usecase/otherpath"), handler::listenPOSTUseCase)
            .and(route(GET("/api/otherusercase/path"), handler::listenGETOtherUseCase));*/
    }
}
