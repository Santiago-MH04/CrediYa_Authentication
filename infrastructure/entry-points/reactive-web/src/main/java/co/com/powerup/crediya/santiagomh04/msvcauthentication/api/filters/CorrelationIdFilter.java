package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Component
public class CorrelationIdFilter implements WebFilter {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    private static final String CORRELATION_ID_KEY = "correlationId";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String correlationId = Optional.ofNullable(exchange.getRequest()
            .getHeaders()
            .getFirst(CORRELATION_ID_HEADER))
            .filter(id -> !id.isBlank())
            .orElse(UUID.randomUUID().toString());

        exchange.getAttributes().put(CORRELATION_ID_KEY, correlationId);

        return chain.filter(exchange)
            .contextWrite(ctx -> ctx.put("correlationId", correlationId));
    }
}
