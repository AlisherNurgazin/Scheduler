package kz.iitu.java.userserviceclient.repository;


import kz.iitu.java.userserviceclient.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByIin(String iin);
    boolean existsByDocumentNumber(String documentNumber);
    Optional<Client> findByPhoneNumber(String phoneNumber);
    Optional<Client> findById(UUID id);
}
