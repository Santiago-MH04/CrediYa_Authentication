package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.routersRest;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.AccessTokenResponseDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.UserCredentialsRequestDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.apiHandler.AuthenticationAPIHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class AuthenticationUserRest {

    private final AuthenticationAPIHandler authenticationHandler;

    @Bean
    @RouterOperations({
        @RouterOperation(
            path = "/api/v1/login",
            produces = {"application/json"},
            method = RequestMethod.POST,
            beanClass = AuthenticationAPIHandler.class,
            beanMethod = "listenLoginUseCase",
            operation = @Operation(
                operationId = "Create JWT token",
                summary = "Create token",
                description = "Creates a JSON web token to verify the user's authenticity",
                tags = {"Auth"},
                requestBody = @RequestBody(
                    required = true,
                    description = "Create Token",
                    content = @Content(schema = @Schema(implementation = UserCredentialsRequestDTO.class))
                ),
                responses = {
                    @ApiResponse(responseCode = "201",
                        description = "Token generated successfully",
                        content = @Content(schema = @Schema(implementation = AccessTokenResponseDTO.class))),
                    @ApiResponse(
                        responseCode = "500",
                        description = "Invalid credentials",
                        content = @Content(schema = @Schema(implementation = Map.class))
                    )
                }
            )
        )
    })
    public RouterFunction<ServerResponse> authRouterFunction() {
        return route(POST("/api/v1/login"), this.authenticationHandler::listenLoginUseCase);
    }
}
