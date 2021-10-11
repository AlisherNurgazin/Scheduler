package kz.iitu.java.userserviceclient.payload.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String phoneNumber;
    private String password;
}
