package kz.spring.product.config.security.jwt;

import io.jsonwebtoken.impl.DefaultClaims;
import kz.spring.product.exceptions.CustomAuthenticationException;
import kz.spring.product.service.token.TokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class TokenAuthenticationManager implements AuthenticationManager {
    @Qualifier("userDetailsServiceImpl")
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;


    public TokenAuthenticationManager(@Lazy UserDetailsService userDetailsService, @Lazy TokenService tokenService) {
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return processAuthentication((TokenAuthentication) authentication);
    }

    public TokenAuthentication processAuthentication(TokenAuthentication authentication) throws AuthenticationException {
        String token = authentication.getToken();
        if (!tokenService.tokenValidation(token))
            throw new CustomAuthenticationException("Bad or expired token");
        return buildFullTokenAuthentication(authentication, token);
    }

    private TokenAuthentication buildFullTokenAuthentication(TokenAuthentication authentication, String token) {
        DefaultClaims claims = tokenService.getClaimsFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.get("username", String.class));
        if (!userDetails.isAccountNonLocked()) {
            throw new CustomAuthenticationException("User is blocked");
        }
        else if (!userDetails.isEnabled()){
            throw new CustomAuthenticationException("User is not activated");
        }
        else {
            return new TokenAuthentication(authentication.getToken(), true, userDetails);
        }
    }


}
