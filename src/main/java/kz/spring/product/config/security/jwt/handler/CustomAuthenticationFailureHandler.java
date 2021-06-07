package kz.spring.product.config.security.jwt.handler;

import kz.spring.product.model.dto.response.FailureResponse;
import kz.spring.product.service.util.BodyWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        FailureResponse failureResponse = new FailureResponse(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                e.getLocalizedMessage(),
                httpServletRequest.getRequestURI()
        );
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        BodyWriter.bodyWriter(httpServletResponse.getWriter() , failureResponse , FailureResponse.class);
    }
}
