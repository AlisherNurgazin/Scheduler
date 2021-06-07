package kz.spring.product.model.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    public TokenResponse(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
