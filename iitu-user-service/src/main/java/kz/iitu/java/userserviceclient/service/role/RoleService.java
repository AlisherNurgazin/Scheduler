package kz.iitu.java.userserviceclient.service.role;

import kz.iitu.java.userserviceclient.domain.Role;
import kz.iitu.java.userserviceclient.exceptions.CustomNotFoundException;
import kz.iitu.java.userserviceclient.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service @RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public Role findByName(String name){
        return repository.findByName(name).orElseThrow(()->new CustomNotFoundException("Роль не найдена"));
    }

    @PostConstruct
    private void save(){
        if (!repository.existsByName("ROLE_USER")){
            Role role = new Role("ROLE_USER");
            repository.save(role);
        }
    }
}
