package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.apiHandler;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.UserRequestDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.UserResponseDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.loggingHelpers.ReactiveHandlerSupport;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.loggingHelpers.ReactiveMDC;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.loggingHelpers.loggingEnums.LogLevel;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.mappers.UserApiMapper;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserAPIHandler {

    private final UserUseCase userUseCase;
    private final UserApiMapper userApiMapper;

    private final ReactiveHandlerSupport loggingSupport;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");
    }

    /*public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        return ReactiveMDC.withLoggingContext(correlationId ->
            serverRequest.bodyToMono(UserRequestDTO.class)
                .doOnNext(dto -> ReactiveMDC.log(LogLevel.INFO, "📥 Incoming user creation request: {}", dto))
                .map(this.userApiMapper::toDomain)
                .flatMap(this.userUseCase::createUser)
                .doOnNext(user -> ReactiveMDC.log(LogLevel.INFO, "✅ User created successfully with id {}", user.getId()))
                .map(this.userApiMapper::toResponse)
                .flatMap(userResponseDTO ->
                    ServerResponse.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("correlationId", correlationId)
                    .bodyValue(userResponseDTO)
                )
        );
    }*/

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        Mono<UserResponseDTO> logic = serverRequest.bodyToMono(UserRequestDTO.class)
            .doOnNext(dto -> ReactiveMDC.log(LogLevel.INFO, "📥 Incoming user creation request: {}", dto))
            .map(userApiMapper::toDomain)
            .flatMap(userUseCase::createUser)
            .doOnNext(user -> ReactiveMDC.log(LogLevel.INFO, "✅ User created successfully with id {}", user.getId()))
            .map(userApiMapper::toResponse);

        return this.loggingSupport.handleRequest(serverRequest, logic, HttpStatus.CREATED, "✅ User created successfully");
    }
}
