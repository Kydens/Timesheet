package com.example.timesheetreport.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.timesheetreport.controllers.response.ApiResponse;
import com.example.timesheetreport.controllers.response.Response;
import com.example.timesheetreport.models.User;
import com.example.timesheetreport.models.DTO.UserDTO;
import com.example.timesheetreport.repositories.UserRepository;
import com.example.timesheetreport.repositories.RoleRepository;
import com.example.timesheetreport.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ApiResponse apiResponse;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtils;

    @Autowired
    public AuthController(ApiResponse apiResponse, RoleRepository roleRepository, UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtil jwtUtils) {
        this.apiResponse = apiResponse;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("signin")
    public ResponseEntity<Response<String>> login(@RequestBody UserDTO userDTO) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            // cara untuk dapetin role nya
            String role = auth.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse(null);

            return apiResponse.success(200, "Login Berhasil", jwtUtils.generateToken(userDetails.getUsername(), role));
        } catch (Exception e) {
            return apiResponse.error(401, "Gagal Login", e.getMessage());
        }
    }

    // @PostMapping("signup")
    // public ResponseEntity<Response<String>> register(@RequestBody UserDTO userDTO) {
    //     try {
    //         User user = new User();

    //         user.setEmail_company(userDTO.getEmailCompany());
    //         user.setUsername(userDTO.getUsername());
    //         user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    //         user.setRole(roleRepository.findById(userDTO.getRoleId()).orElseThrow(() -> new RuntimeException("Role not found")));

    //         userRepository.save(user);

    //         return apiResponse.success(200, "Signup Berhasil");
    //     } catch (Exception e) {
    //         return apiResponse.error(400, "Gagal Signup", e.getMessage());
    //     }
    // }
}