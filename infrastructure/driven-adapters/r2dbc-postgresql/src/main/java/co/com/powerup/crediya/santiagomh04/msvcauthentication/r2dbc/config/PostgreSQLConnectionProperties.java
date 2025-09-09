package co.com.powerup.crediya.santiagomh04.msvcauthentication.r2dbc.config;

// TODO: Load properties from the application.yaml file or from secrets manager
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapters.r2dbc")
public record PostgreSQLConnectionProperties(
    String host,
    Integer port,
    String database,
    String schema,
    String username,
    String password) {
}
