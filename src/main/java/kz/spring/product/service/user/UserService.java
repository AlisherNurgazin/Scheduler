package kz.spring.product.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import kz.spring.product.model.User;
import kz.spring.product.model.dto.request.LoginRequest;
import kz.spring.product.model.dto.request.RegisterRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {
    ResponseEntity<?> login(LoginRequest loginRequest);
    User getByLogin(String login);
    ResponseEntity<?> register(RegisterRequest request);
    User getAuth();
    Map<String , Object> refreshToken(String token);
    User findById(String id);
    void save(User user);
}
