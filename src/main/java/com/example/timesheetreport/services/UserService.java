package com.example.timesheetreport.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.timesheetreport.models.User;
import com.example.timesheetreport.models.DTO.UserDTO;
import com.example.timesheetreport.repositories.RoleRepository;
import com.example.timesheetreport.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserDTO> get() {
        return userRepository.get();
    }

    public UserDTO get(Integer id) {
        return userRepository.get(id);
    }

    public UserDTO save(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setRole(roleRepository.findById(userDTO.getRoleId()).orElseThrow(() -> new RuntimeException("Role not found")));

        User resultUser = userRepository.save(user);

        return get(resultUser.getId());
    }

    public Boolean remove(Integer id) {
        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            user.setIs_deleted(true);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(GrantedAuthority authority) {
        Set<GrantedAuthority> grantedAuthority = new HashSet<>();
        grantedAuthority.add(authority);
        return grantedAuthority;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user.equals(null)) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        String roleName = "ROLE_" + user.getRole().getName().toUpperCase();

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), 
            user.getPassword(), 
            Set.of(new SimpleGrantedAuthority(roleName))
        );
    }
}