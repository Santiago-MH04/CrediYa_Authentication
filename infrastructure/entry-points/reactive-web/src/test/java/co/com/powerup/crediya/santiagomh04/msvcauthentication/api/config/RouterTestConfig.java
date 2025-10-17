package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.config;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.routersRest.UserRouterRest;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.apiHandler.UserAPIHandler;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.loggingHelpers.HandlerLoggingSupport;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.mappers.UserApiMapper;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.paths.UserPaths;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.user.UserUseCase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
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
            when(mockUserPaths.getUserByIdentificationNumber()).thenReturn("/api/v1/users/{identificationNumber}");
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
    public UserRouterRest routerRest(UserPaths userPaths, UserAPIHandler userAPIHandler) {
        return new UserRouterRest(userPaths, userAPIHandler);
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserRouterRest routerRest) {
        return routerRest.routerFunction();
    }
}
