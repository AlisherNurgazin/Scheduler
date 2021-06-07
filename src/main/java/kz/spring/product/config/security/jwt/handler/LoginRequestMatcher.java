package kz.spring.product.config.security.jwt.handler;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class LoginRequestMatcher implements RequestMatcher {
    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getRequestURI().contains("/error"))
            return false;
        if (httpServletRequest.getRequestURI().contains("/account"))
            return false;
        if (httpServletRequest.getRequestURI().contains("/info"))
            return false;
        if (httpServletRequest.getRequestURI().contains("/admin-system"))
            return false;
        return true;
    }
}
