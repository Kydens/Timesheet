package com.example.timesheetreport.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.timesheetreport.models.Role;
import com.example.timesheetreport.models.DTO.RoleDTO;
import com.example.timesheetreport.repositories.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDTO> get() {
        return roleRepository.get();
    }

    public RoleDTO get(Integer id) {
        return roleRepository.get(id);
    }

    public RoleDTO save(RoleDTO roleDTO) {
        Role role = new Role();

        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());

        Role resultRole = roleRepository.save(role);

        return get(resultRole.getId());
    }

    public Boolean remove(Integer id) {
        Role role = roleRepository.findById(id).orElse(null);

        if (role != null) {
            role.setIs_deleted(true);
            roleRepository.save(role);
            return true;
        }

        return false;
    }
}
