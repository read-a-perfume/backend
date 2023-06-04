package io.perfume.api.common.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.perfume.api.base.CustomHttpException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomHttpException e) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e);
        } catch (Exception e) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, CustomHttpException e) {
        response.setStatus(status.value());
        response.setContentType("application/json");

        Map<String, Object> error = Map.of(
                "status", e.getHttpStatus().getReasonPhrase(),
                "statusCode", e.getHttpStatus().value(),
                "error", e.getMessage()
        );

        try {
            String json = objectMapper.writeValueAsString(error);
            response.getWriter().write(json);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable e) {
        response.setStatus(status.value());
        response.setContentType("application/json");

        Map<String, Object> error = Map.of(
                "status", HttpStatus.INTERNAL_SERVER_ERROR,
                "statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "error", e.getMessage()
        );
        try {
            String json = objectMapper.writeValueAsString(error);
            response.getWriter().write(json);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
