package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.apiHandler;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.UserRequestDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.UserResponseDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.loggingHelpers.HandlerLoggingSupport;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.mappers.UserApiMapper;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserAPIHandler {

    private final UserUseCase userUseCase;
    private final UserApiMapper userApiMapper;

    private final HandlerLoggingSupport loggingSupport;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        return Mono.fromCallable(() -> serverRequest.pathVariable("identificationNumber"))
            .map(String::trim)
            .filter(item -> !item.isBlank())
            .flatMap(this.userUseCase::findByIdentificationNumber)
            .map(this.userApiMapper::toResponse)
            .flatMap(dto -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
            );
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        Mono<UserResponseDTO> logic = serverRequest.bodyToMono(UserRequestDTO.class)
            .map(this.userApiMapper::toDomain)
            .flatMap(this.userUseCase::createUser)
            .map(this.userApiMapper::toResponse);

        return this.loggingSupport.handleRequest(
            logic,
            HttpStatus.CREATED,
            "📥 Incoming user creation request",
            "✅ User created successfully with id",
            UserResponseDTO::id // 👈 Only its ID is logged
        );
    }
}
