package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.apiHandler;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.UserCredentialsRequestDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.mappers.UserCredentialsMapper;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.authentication.AuthenticationUseCase;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthenticationAPIHandler {

    private final AuthenticationUseCase authenticationUseCase;

    private final Validator validator;

    private final UserCredentialsMapper userCredentialsMapper;

    public Mono<ServerResponse> listenLoginUseCase(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserCredentialsRequestDTO.class)
            .flatMap(loginRequest -> {
                Set<ConstraintViolation<UserCredentialsRequestDTO>> violations = this.validator.validate(loginRequest);
                if (!violations.isEmpty()) {
                    return Mono.error(new ConstraintViolationException(violations));
                }
                return Mono.just(loginRequest);
            })
            .map(this.userCredentialsMapper::toDomain)
            .flatMap(this.authenticationUseCase::login)
            .map(this.userCredentialsMapper::toResponse)
            .flatMap(loginResponse -> ServerResponse
                .ok()
                    /*.status(HttpStatus.STATUS)*/  // In order to force to respond with any other status
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .bodyValue(loginResponse)
            );
    }
}
