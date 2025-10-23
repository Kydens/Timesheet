package com.example.timesheetreport.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.timesheetreport.models.Employee;
import com.example.timesheetreport.models.User;
import com.example.timesheetreport.models.DTO.EmployeeDTO;
import com.example.timesheetreport.models.DTO.UserDTO;
import com.example.timesheetreport.repositories.DivisionRepository;
import com.example.timesheetreport.repositories.EmployeeRepository;
import com.example.timesheetreport.repositories.RoleRepository;
import com.example.timesheetreport.repositories.UserRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DivisionRepository divisionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, DivisionRepository divisionRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.divisionRepository = divisionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String getManager(String username) {
        return employeeRepository.getEmployeeByUsername(username).getManagerName();
    }

    public List<EmployeeDTO> get() {
        return employeeRepository.get();
    }

    public EmployeeDTO get(Integer id) {
        return employeeRepository.get(id);
    }

    public EmployeeDTO save(EmployeeDTO employeeDTO, UserDTO userDTO) {
        Employee employee = new Employee();

        employee.setId(employeeDTO.getId());
        employee.setDivision(divisionRepository.findById(employeeDTO.getDivisionId()).orElseThrow(() -> new EntityNotFoundException("Division not found")));
        if (employeeDTO.getManagerId() != null) {
            employee.setManager(
                employeeRepository.findById(employeeDTO.getManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found"))
            );
        } else {
            employee.setManager(null);
        }
        employee.setName(employeeDTO.getName());
        employee.setAddress(employeeDTO.getAddress());
        employee.setPhone_number(employeeDTO.getPhoneNumber());
        employee.setDate_birth(employeeDTO.getDateBirth());

        employeeRepository.save(employee);

        User user = new User();

        user.setEmployee(employee);
        user.setEmail_company(userDTO.getEmailCompany());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(roleRepository.findById(userDTO.getRoleId()).orElseThrow(() -> new EntityNotFoundException("Role not found")));

        userRepository.save(user);

        return get(employee.getId());
    }

    public Boolean remove(Integer id) {
        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee != null) {
            employee.setIs_deleted(true);
            employeeRepository.save(employee);
            return true;
        }

        return false;
    }
}
