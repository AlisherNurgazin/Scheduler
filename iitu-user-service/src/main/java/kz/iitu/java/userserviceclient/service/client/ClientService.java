package kz.iitu.java.userserviceclient.service.client;


import kz.iitu.java.userserviceclient.domain.Client;
import kz.iitu.java.userserviceclient.payload.request.ClientFullInfoRequest;
import kz.iitu.java.userserviceclient.payload.request.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface ClientService {
    void save(Client client);
    ResponseEntity<?> register(RegisterRequest request);
    ResponseEntity<?> saveFullInfo(String id , ClientFullInfoRequest request);
    Client findByPhoneNumber(String phoneNumber);
    Client findById(String id);
}
