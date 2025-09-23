package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.config;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.RouterRest;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.apiHandler.UserAPIHandler;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.loggingHelpers.HandlerLoggingSupport;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.mappers.UserApiMapper;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.paths.UserPaths;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.user.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
/*@ComponentScan(basePackageClasses = {
    UserAPIHandler.class,
    HandlerLoggingSupport.class
})*/
public class RouterTestConfig {
    @Bean
    public UserUseCase userUseCase() {
        return mock(UserUseCase.class);
    }

    @Bean
    public UserApiMapper userApiMapper() {
        return mock(UserApiMapper.class);
    }

    @Bean
    public HandlerLoggingSupport handlerLoggingSupport() {
        return mock(HandlerLoggingSupport.class);
    }

    @Bean
    public UserPaths userPaths() {
        UserPaths mockUserPaths = mock(UserPaths.class);
        when(mockUserPaths.getUsers()).thenReturn("/api/v1/users");
        return mockUserPaths;
    }

    @Bean
    public UserAPIHandler userAPIHandler(
        UserUseCase userUseCase,
        UserApiMapper userApiMapper,
        HandlerLoggingSupport loggingSupport
    ) {
        return new UserAPIHandler(userUseCase, userApiMapper, loggingSupport);
    }

    @Bean
    public RouterRest routerRest(UserPaths userPaths, UserAPIHandler userAPIHandler) {
        return new RouterRest(userPaths, userAPIHandler);
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction(RouterRest routerRest) {
        return routerRest.routerFunction();
    }
}
