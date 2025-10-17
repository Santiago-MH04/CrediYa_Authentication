package co.com.powerup.crediya.santiagomh04.msvcauthentication.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationManager jwtAuthenticationManager;

    private final JwtSecurityContextRepository jwtSecurityContextRepository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .authenticationManager(this.jwtAuthenticationManager)
            .securityContextRepository(this.jwtSecurityContextRepository)
            .authorizeExchange(exchange ->
                exchange
                    .pathMatchers(HttpMethod.GET,"/api/v1/users/{identificationNumber}")/*.hasRole("APPLICANT")*/.permitAll()
                    .pathMatchers(HttpMethod.GET,"/api/v1/users/email/{email}").hasAnyRole("ADMIN", "AGENT")
                    .pathMatchers(HttpMethod.POST, "/api/v1/users").hasAnyRole("ADMIN", "AGENT")
                    .pathMatchers("/api/v1/login").permitAll()

                    .pathMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/v3/api-docs.yaml",
                        "/swagger-resources/**",
                        "/webjars/**"
                    ).permitAll()
                    .anyExchange().authenticated()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(unauthorisedHandler())
                .accessDeniedHandler(forbiddenHandler()))
            .build();
    }

    @Bean
    public ServerAuthenticationEntryPoint unauthorisedHandler() {
        return ((exchange, ex) -> {
            ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> body = new HashMap<>();
                body.put("status", HttpStatus.UNAUTHORIZED.toString().toLowerCase().replaceAll("_", ""));
                body.put("message", "You don’t count on valid credentials");

            byte[] bytes = writeJson(body);
            return response.writeWith(
                Mono.just(response.bufferFactory().wrap(bytes))
            );
        });
    }

    @Bean
    public ServerAccessDeniedHandler forbiddenHandler() {
        return (exchange, denied) -> {
            ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.FORBIDDEN);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> body = new HashMap<>();
                body.put("status", HttpStatus.FORBIDDEN.toString().toLowerCase().replaceAll("_", ""));
                body.put("message", "You don’t have enough permissions to access to this resource");

            byte[] bytes = writeJson(body);
            return response.writeWith(
                Mono.just(response.bufferFactory().wrap(bytes))
            );
        };
    }

    private byte[] writeJson(Map<String, Object> body) {
        try {
            return new ObjectMapper().writeValueAsString(body).getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            return ("{\"error\":\"Serialisation error\"}").getBytes(StandardCharsets.UTF_8);
        }
    }
}
