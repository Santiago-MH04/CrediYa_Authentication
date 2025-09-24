package co.com.powerup.crediya.santiagomh04.msvcauthentication.api;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.ErrorResponseDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.UserRequestDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.UserResponseDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.apiHandler.UserAPIHandler;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.paths.UserPaths;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
@Tag(name = "Users management", description = "Operations related to user creation and retrieval")
public class RouterRest {

    private final UserPaths userPaths;
    private final UserAPIHandler userHandler;

    @Bean
    @RouterOperations({
        @RouterOperation(
            path = "/api/v1/users",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST,
            beanClass = UserAPIHandler.class,
            beanMethod = "listenPOSTUseCase",
            operation = @Operation(
                operationId = "registerUser",
                summary = "Register a new user",
                description = "Registers a new user with the information provided.",
                tags = {"User"},
                requestBody = @RequestBody(
                    required = true,
                    description = "User registration request",
                    content = @Content(schema = @Schema(implementation = UserRequestDTO .class))
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "201",
                        description = "User registered successfully",
                        content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
                    ),
                    @ApiResponse(
                        responseCode = "400",
                        description = "Invalid user details provided",
                        content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
                }
            )
        ),
        /*@RouterOperation()*/
    })
    public RouterFunction<ServerResponse> routerFunction() {
        return route(POST(this.userPaths.getUsers()), this.userHandler::listenPOSTUseCase)
            .andRoute(GET(this.userPaths.getUserByIdentificationNumber()), this.userHandler::listenGETUseCase);

        /*return route(GET("/api/usecase/path"), handler::listenGETUseCase)
            .andRoute(POST("/api/usecase/otherpath"), handler::listenPOSTUseCase)
            .and(route(GET("/api/otherusercase/path"), handler::listenGETOtherUseCase));*/
    }
}
