package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.loggingHelpers;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.ErrorResponseDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.loggingHelpers.loggingEnums.LogLevel;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class ReactiveHandlerSupport {
    public <T> Mono<ServerResponse> handleRequest(
        ServerRequest request,
        Mono<T> businessLogic,
        HttpStatus successStatus,
        String successMessage
    ) {
        return Mono.deferContextual(ctx -> {
            String correlationId = ctx.getOrDefault("correlationId", "unknown");
            MDC.put("correlationId", correlationId);

            return businessLogic
                .doOnNext(result -> ReactiveMDC.log(LogLevel.INFO, successMessage, result))
                .flatMap(result ->
                    ServerResponse.status(successStatus)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("correlationId", correlationId)
                        .bodyValue(result)
                )
                .doFinally(signal -> MDC.clear());
        });
    }

    public Mono<ServerResponse> handleError(
        ServerRequest request,
        Throwable error,
        HttpStatus status
    ) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            LocalDateTime.now(),
            request.path(),
            error.getMessage(),
            status.toString()
        );

        return ReactiveMDC.withErrorLogging(correlationId ->
            ServerResponse.status(status.value())
                .contentType(MediaType.APPLICATION_JSON)
                .header("correlationId", correlationId)
                .bodyValue(errorResponse),
            request,
            error
        );
    }
}
