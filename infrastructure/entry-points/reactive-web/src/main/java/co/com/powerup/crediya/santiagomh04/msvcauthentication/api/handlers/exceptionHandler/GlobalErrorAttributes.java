package co.com.powerup.crediya.santiagomh04.msvcauthentication.api.handlers.exceptionHandler;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable error = getError(request);
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
            errorAttributes.put("timestamp", LocalDateTime.now());
            errorAttributes.put("path", request.path());
            errorAttributes.put("message", error.getMessage());
            errorAttributes.put("status", HttpStatus.BAD_REQUEST.toString());

        return errorAttributes;
    }
}
