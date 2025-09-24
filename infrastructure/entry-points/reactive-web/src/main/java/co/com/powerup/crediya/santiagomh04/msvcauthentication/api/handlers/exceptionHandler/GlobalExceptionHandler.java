package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.exceptionHandler;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.loggingHelpers.HandlerLoggingSupport;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.exceptions.business.BusinessException;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.exceptions.validation.ValidationException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Order(-2)
@Component
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    private final HandlerLoggingSupport loggingSupport;

    private static final Map<Class<? extends Throwable>, HttpStatus> EXCEPTION_STATUS_MAP = Map.ofEntries(
        Map.entry(ValidationException.class, HttpStatus.BAD_REQUEST),
        Map.entry(BusinessException.class, HttpStatus.NOT_FOUND)
        // Add more exceptions here and their HttpStatus codes when necessary
    );

    public GlobalExceptionHandler(
        ErrorAttributes errorAttributes,
        ApplicationContext applicationContext,
        ServerCodecConfigurer serverCodecConfigurer,
        HandlerLoggingSupport loggingSupport
    ) {
        super(errorAttributes, new WebProperties.Resources(), applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
        this.setMessageReaders(serverCodecConfigurer.getReaders());
        this.loggingSupport = loggingSupport;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Throwable error = getError(request);
        HttpStatus status = determineHttpStatus(error);

        return this.loggingSupport.handleError(
            request,
            error,
            status
        );
    }

    private HttpStatus determineHttpStatus(Throwable error) {
        return EXCEPTION_STATUS_MAP.getOrDefault(error.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
