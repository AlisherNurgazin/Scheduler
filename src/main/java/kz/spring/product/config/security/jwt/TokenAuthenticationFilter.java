package kz.spring.product.config.security.jwt;

import kz.spring.product.config.security.jwt.handler.CustomAuthenticationFailureHandler;
import kz.spring.product.config.security.jwt.handler.CustomAuthenticationSuccessHandler;
import kz.spring.product.config.security.jwt.handler.LoginRequestMatcher;
import org.apache.commons.io.Charsets;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public TokenAuthenticationFilter() {
        super(new LoginRequestMatcher());
        setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
        setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        Authentication authentication;
        String token = httpServletRequest.getHeader("Authorization");

        authentication = getAuthenticationManager().authenticate(new TokenAuthentication(token));

        return authentication;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        res.setCharacterEncoding(Charsets.UTF_8.name());
        super.doFilter(req, res, chain);
    }


}
