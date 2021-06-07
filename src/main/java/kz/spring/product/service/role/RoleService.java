package kz.spring.product.service.role;


import kz.spring.product.model.Role;

public interface RoleService {
    Role findByName(String name);
}
