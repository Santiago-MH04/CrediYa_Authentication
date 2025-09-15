package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.loggingHelpers;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.ErrorResponseDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.loggingHelpers.loggingEnums.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.function.Function;

@Component
public class HandlerLoggingSupport {

    public <T> Mono<ServerResponse> handleRequest(
        Mono<T> businessLogic,
        HttpStatus successStatus,
        String entryMessage,
        String successMessage,
        Function<T, Object> successLogExtractor
    ) {
        LoggerManager.log(LogLevel.INFO, entryMessage);

        return businessLogic
            .doOnNext(result -> {
                Object extracted = successLogExtractor.apply(result);
                LoggerManager.log(LogLevel.INFO, successMessage + ": {}", extracted);
            })
            .flatMap(result -> ServerResponse.status(successStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(result)
            );
    }
        public <T> Mono<ServerResponse> handleRequest(  //This is optional, just in case I decide not to extract information
            Mono<T> businessLogic,
            HttpStatus successStatus,
            String entryMessage,
            String successMessage
        ) {
            LoggerManager.log(LogLevel.INFO, entryMessage);

            return businessLogic
                .doOnNext(result -> LoggerManager.log(LogLevel.INFO, successMessage))
                .flatMap(result -> ServerResponse.status(successStatus)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(result)
                );
        }

    public Mono<ServerResponse> handleError(
        ServerRequest request,
        Throwable error,
        HttpStatus status
    ) {
        LoggerManager.log(LogLevel.ERROR, "❌ Error occurred: {}", error.getMessage());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            LocalDateTime.now(),
            request.path(),
            error.getMessage(),
            status.toString()
        );

        return ServerResponse.status(status.value())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(errorResponse);
    }


}
