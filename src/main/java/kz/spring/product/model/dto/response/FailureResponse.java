package kz.spring.product.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FailureResponse {
    private String timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public FailureResponse(Integer status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now().toString();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
