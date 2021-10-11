package kz.iitu.java.userserviceclient.controller;

import kz.iitu.java.userserviceclient.domain.Client;
import kz.iitu.java.userserviceclient.payload.request.ClientFullInfoRequest;
import kz.iitu.java.userserviceclient.payload.request.RegisterRequest;
import kz.iitu.java.userserviceclient.service.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/user") @RequiredArgsConstructor
public class UserController {

    private final ClientService service;

    @GetMapping(value = "/find/by/phoneNumber/{phoneNumber}")
    public Client findByPhoneNumber(@PathVariable String phoneNumber){
        return service.findByPhoneNumber(phoneNumber);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return service.register(request);
    }

    @PostMapping(value = "/save/full/info/{id}")
    public ResponseEntity<?> saveFullInfo(@PathVariable String id , @RequestBody ClientFullInfoRequest request){
        return service.saveFullInfo(id, request);
    }
}
