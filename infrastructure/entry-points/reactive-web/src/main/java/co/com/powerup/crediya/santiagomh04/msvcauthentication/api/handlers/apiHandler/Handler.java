package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.apiHandler;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.UserRequestDTO;
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
public class Handler {

    private final UserUseCase userUseCase;
    private final UserApiMapper userApiMapper;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserRequestDTO.class)
            .map(this.userApiMapper::toDomain)
            .flatMap(this.userUseCase::createUser)
            .map(this.userApiMapper::toResponse)
            .flatMap(userResponseDTO ->
                ServerResponse.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(userResponseDTO)
            );
    }
}
