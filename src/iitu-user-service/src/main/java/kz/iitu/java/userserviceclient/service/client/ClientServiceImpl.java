package kz.iitu.java.userserviceclient.service.client;


import kz.iitu.java.userserviceclient.constants.ClientStatusConstants;
import kz.iitu.java.userserviceclient.domain.Client;
import kz.iitu.java.userserviceclient.domain.Role;
import kz.iitu.java.userserviceclient.exceptions.CustomConflictException;
import kz.iitu.java.userserviceclient.exceptions.CustomNotFoundException;
import kz.iitu.java.userserviceclient.payload.request.ClientFullInfoRequest;
import kz.iitu.java.userserviceclient.payload.request.RegisterRequest;
import kz.iitu.java.userserviceclient.repository.ClientRepository;
import kz.iitu.java.userserviceclient.service.role.RoleService;
import kz.iitu.java.userserviceclient.service.validation.ValidationService;
import kz.iitu.java.userserviceclient.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final RoleService roleService;

    @Override
    public void save(Client client) {
        repository.save(client);
    }

    @Override
    public ResponseEntity<?> register(RegisterRequest request) {
        ValidationService.registrationValidation(request);
        if (repository.existsByPhoneNumber(request.getPhoneNumber()))
            throw new CustomConflictException("Номер телефон уже занят");
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(roleService.findByName("ROLE_USER"));
        Client client = Client.builder()
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .status(ClientStatusConstants.ACTIVE)
                .roles(roles)
                .build();
        save(client);
        return ResponseUtil.response("Пользователь успешно зарегистрирован");
    }

    @Override
    public ResponseEntity<?> saveFullInfo(String id , ClientFullInfoRequest request) {
        ValidationService.saveClientInfoValidation(request);
        Client client = findById(id);
        if (client.getIin() != null)
            if (!client.getIin().equals(request.getIin()))
                if (repository.existsByIin(request.getIin()))
                    throw new CustomConflictException("ИИН занят");
        if (client.getDocumentNumber() != null)
            if (!client.getDocumentNumber().equals(request.getDocumentNumber()))
                if (repository.existsByDocumentNumber(request.getDocumentNumber()))
                    throw new CustomConflictException("Номер документа занят");
        clientFullInfoSetter(client, request);
        save(client);
        return ResponseUtil.response("Ваши данные успешно сохранены");
    }

    @Override
    public Client findByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new CustomNotFoundException(
                "Пользователь не найден"
        ));
    }

    @Override
    public Client findById(String id) {
        return repository.findById(UUID.fromString(id)).orElseThrow(() -> new CustomNotFoundException("Пользователь не найден"));
    }

    private void clientFullInfoSetter(Client client , ClientFullInfoRequest request){
        client.setIin(request.getIin());
        client.setLastname(request.getLastname());
        client.setFirstname(request.getFirstname());
        client.setMiddleName(request.getMiddleName());
        client.setBirthDate(request.getBirthDate());
        client.setGender(request.getGender());
        client.setDocumentNumber(request.getDocumentNumber());
        client.setDocumentIssuedBy(request.getDocumentIssuedBy());
        client.setDocumentIssuedDate(request.getDocumentIssuedDate());
        client.setDocumentValidUntilDate(request.getDocumentValidUntilDate());
        client.setSalary(request.getSalary());
        client.setCommunalPayment(request.getCommunalPayment());
    }
}
