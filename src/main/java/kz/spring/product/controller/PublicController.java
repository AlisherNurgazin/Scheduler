package kz.spring.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kz.spring.product.model.dto.request.LoginRequest;
import kz.spring.product.model.dto.request.RegisterRequest;
import kz.spring.product.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {

    private final UserService userService;

    @Autowired
    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return userService.register(request);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return userService.login(request);
    }

    @PostMapping(value = "/refresh/token")
    public Map<String  , Object> refreshToken(@RequestBody Map<String  , String > refreshToken){
        return userService.refreshToken(refreshToken.get("refreshToken"));
    }
}
