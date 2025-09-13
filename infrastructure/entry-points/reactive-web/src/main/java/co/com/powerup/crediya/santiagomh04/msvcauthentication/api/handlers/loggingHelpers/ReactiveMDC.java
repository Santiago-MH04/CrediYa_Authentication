package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.loggingHelpers;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.loggingHelpers.loggingEnums.LogLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.function.Function;

//MDC stands for Mapped Diagnostic Context
public class ReactiveMDC {

    private static final Logger log = LogManager.getLogger(ReactiveMDC.class);

    /*public static <T> Mono<T> withLoggingContext(Function<String, Mono<T>> block) {
        return Mono.deferContextual(ctx -> {
            String correlationId = ctx.getOrDefault("correlationId", "unknown");
            MDC.put("correlationId", correlationId);
            try {
                return block.apply(correlationId);
            } finally {
                MDC.clear();    //To clean the MDC in case of leaks among threads
            }
        });
    }*/

    /*public static <T> Mono<T> withLoggingAndErrorHandling(
        Function<String, Mono<T>> block,
        String errorMessage
    ) {
        return Mono.deferContextual(ctx -> {
            String correlationId = ctx.getOrDefault("correlationId", "unknown");
            MDC.put("correlationId", correlationId);

            return block.apply(correlationId)
                .doOnError(e -> log.error("{} [correlationId={}]: {}", errorMessage, correlationId, e.getMessage(), e))
                .doFinally(signal -> MDC.clear());  //To clean the MDC in case of leaks among threads
        });
    }*/

    public static <T> Mono<T> withErrorLogging(
        Function<String, Mono<T>> block,
        ServerRequest request,
        Throwable error
    ) {
        return Mono.deferContextual(ctx -> {
            String correlationId = ctx.getOrDefault("correlationId", "unknown");
            MDC.put("correlationId", correlationId);

            String path = request.path();
            String errorMessage = String.format("❌ Error at path %s: %s", path, error.getMessage());

            ReactiveMDC.log(LogLevel.ERROR, errorMessage, error);

            return block.apply(correlationId)
                .doFinally(signal -> MDC.clear());  //To clean the MDC in case of leaks among threads
        });
    }

    public static void log(LogLevel level, String message, Object... args) {
        Mono.deferContextual(ctx -> {
            String correlationId = ctx.getOrDefault("correlationId", "unknown");
            MDC.put("correlationId", correlationId);

            String formattedMessage = String.format("%s [correlationId=%s]", message, correlationId);

            switch (level) {
                case INFO -> log.info(formattedMessage, args);
                case ERROR -> log.error(formattedMessage, args);
                case DEBUG -> log.debug(formattedMessage, args);
                case WARN  -> log.warn(formattedMessage, args);
                case TRACE -> log.trace(formattedMessage, args);
            }

            MDC.clear();
            return Mono.empty();
        }).subscribe();
    }
}
