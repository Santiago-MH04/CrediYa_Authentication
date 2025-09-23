package co.com.powerup.crediya.santiagomh04.msvcauthentication.api;

import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.config.RouterTestConfig;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.RoleDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.UserRequestDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.dto.UserResponseDTO;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.loggingHelpers.HandlerLoggingSupport;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.api.mappers.UserApiMapper;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.exceptions.validation.ValidationException;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.model.user.User;
import co.com.powerup.crediya.santiagomh04.msvcauthentication.usecase.user.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*@WebFluxTest
@Import(RouterTestConfig.class)*/
/*@ContextConfiguration(classes = {RouterRest.class, UserAPIHandler.class, UserPaths.class})*/

/*@SpringBootTest(classes = RouterTestConfig.class)
@AutoConfigureWebTestClient*/

/*@ExtendWith(MockitoExtension.class)*/
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RouterTestConfig.class})
/*@AutoConfigureWebTestClient*/
class RouterRestTest {

    @Autowired
    private RouterRest routerRest;

    @Autowired
    RouterFunction<ServerResponse> routerFunction;

    private WebTestClient webTestClient;

    @Autowired
    private UserUseCase userUseCase;

    @Autowired
    private UserApiMapper userApiMapper;

    @Autowired
    private HandlerLoggingSupport loggingSupport;

    private UserRequestDTO requestDTO;
    private UserResponseDTO responseDTO;
    private User userDomain;
    private Mono<ServerResponse> mockedResponse;

    @BeforeEach
    void setUp() {
        this.requestDTO = new UserRequestDTO(
            "Pepito",
            "Pérez",
            "Calle falsa 123",
            "3011234567",
            LocalDate.of(1990, 12, 30),
            new BigInteger("1000000"),
            "user@example.com",
            "Password123*",
            "CC",
            "1035432567"
        );
        this.responseDTO = new UserResponseDTO(
            "random-generated-id",
            "Pepito",
            "Pérez",
            "Calle falsa 123",
            "3011234567",
            "CC",
            "1035432567",
            "user@example.com",
            LocalDate.of(1990, 12, 30),
            new BigInteger("1000000"),
            new RoleDTO(1L, "ROLE_APPLICANT", "Represents a user who is applying for a loan")
        );
        this.userDomain = User.builder()
            .id("random-generated-id")
            .name("Pepito")
            .lastName("Pérez")
            .address("Calle falsa 123")
            .telephone("3011234567")
            .dateOfBirth(LocalDate.of(1990, 12, 30))
            .baseSalary(new BigInteger("1000000"))
            .documentType(User.DocumentType.CC)
            .identificationNumber("1035432567")
            .email("user@example.com")
            .build();

        this.mockedResponse = ServerResponse
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(responseDTO);

        when(this.userApiMapper.toDomain(any(UserRequestDTO.class))).thenReturn(this.userDomain);
        when(this.userUseCase.createUser(any(User.class))).thenReturn(Mono.just(this.userDomain));
        when(this.userApiMapper.toResponse(any(User.class))).thenReturn(this.responseDTO);
        when(this.loggingSupport.handleRequest(
            any(Mono.class),
            any(HttpStatus.class),
            anyString(),
            anyString(),
            any(Function.class)
        )).thenAnswer(invocation -> {
            Mono<UserResponseDTO> logic = invocation.getArgument(0);

            return logic.flatMap(dto -> ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto));
        });

        this.webTestClient = WebTestClient.bindToRouterFunction(this.routerFunction).build();
    }

    @Test
    void contextLoads() {
        assertNotNull(this.userApiMapper, "❌ The userApiMapper bean was not injected");
        assertNotNull(this.userUseCase, "❌ The userUseCase bean was not injected");
        assertNotNull(this.loggingSupport, "❌ The loggingSupport bean was not injected");
    }

    @Test
    @DisplayName("Should pass if a response body contains the name of the DTOs of request and response")
    void shouldRoutePostUserRequestSuccessfully() {
        this.webTestClient.post()
            .uri("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(this.requestDTO)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(UserResponseDTO.class)
            .consumeWith(result -> {
                UserResponseDTO responseBody = result.getResponseBody();
                assertNotNull(responseBody, "❌ A response body was not received");
                assertEquals("random-generated-id", responseBody.id());
                assertEquals("Pepito", responseBody.name());
                assertEquals("Pérez", responseBody.lastName());
            });
    }

    @Test
    @DisplayName("Should pass if the response contains a null body, because of a validation failure")
    void shouldReturnInternalServerErrorWhenUseCaseFails() {
        when(this.userUseCase.createUser(any(User.class)))
            .thenReturn(Mono.error(new ValidationException("Simulated failure")));

        this.webTestClient.post()
            .uri("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(this.requestDTO)
            .exchange()
            .expectStatus().is5xxServerError()
                /*.expectStatus().is4xxClientError()*/
            .expectBody().isEmpty();
    }

    /*@Test
    void shouldReturnBadRequestWhenValidationFails() {
        when(this.userUseCase.createUser(any(User.class)))
            .thenReturn(Mono.error(new ValidationException("Simulated failure")));

        this.webTestClient.post()
            .uri("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(this.requestDTO)
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody()
            .jsonPath("$.error").isEqualTo("Simulated failure");
    }*/

    /*@Test
    void testListenGETUseCase() {
        webTestClient.get()
            .uri("/api/usecase/path")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .value(userResponse -> {
                    Assertions.assertThat(userResponse).isEmpty();
                }
            );
    }*/

    /*@Test
    void testListenGETOtherUseCase() {
        webTestClient.get()
            .uri("/api/otherusercase/path")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .value(userResponse -> {
                    Assertions.assertThat(userResponse).isEmpty();
                }
            );
    }*/

    /*@Test
    void testListenPOSTUseCase() {
        webTestClient.post()
            .uri("/api/usecase/otherpath")
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue("")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .value(userResponse -> {
                    Assertions.assertThat(userResponse).isEmpty();
                }
            );
    }*/
}
