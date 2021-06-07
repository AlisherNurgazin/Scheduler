package kz.spring.product.service.role;

import kz.spring.product.model.Role;
import kz.spring.product.repository.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }



    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }



}
