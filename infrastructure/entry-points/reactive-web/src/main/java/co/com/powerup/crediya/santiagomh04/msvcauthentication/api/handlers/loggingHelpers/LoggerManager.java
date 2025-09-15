package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.loggingHelpers;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.loggingHelpers.loggingEnums.LogLevel;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

//MDC stands for Mapped Diagnostic Context
@Slf4j
public final class LoggerManager {

    private static final String TRACE_KEY = "traceId";
    private static final String SERVICE_PREFIX = "[msvc-authentication]";

    private LoggerManager() {
        // Private in order not to be instantiated
    }

    public static void log(LogLevel level, String message, Object... args) {
        Mono.deferContextual(ctx -> {
            String traceId = ctx.getOrDefault(TRACE_KEY, null);

            String prefix = SERVICE_PREFIX;
            if (traceId != null) {
                prefix += " [traceId=%s]".formatted(traceId);
            }

            String finalMessage = "%s %s".formatted(prefix, message);
            logInternal(level, finalMessage, args);

            return Mono.empty(); // Just logs
        })
        .subscribe(); // Executes the log in the current context
    }

    public static void logInternal(LogLevel level, String message, Object... args) {
        switch (level) {
            case INFO -> log.info(message, args);
            case ERROR -> log.error(message, args);
            case DEBUG -> log.debug(message, args);
            case WARN  -> log.warn(message, args);
            case TRACE -> log.trace(message, args);
        }
    }
}
