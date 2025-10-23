package com.example.timesheetreport.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.timesheetreport.services.UserService;

@Configuration
public class WebSecurityConfig {
    @Autowired
    UserService userService;

    @Autowired
    private AuthEntryPointJwt unAuthorizedHandler;

    @Bean
    public AuthTokenFilter authJWTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            // .cors(cors -> cors.disable())
            .cors(cors -> cors.configure(http))
            .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(unAuthorizedHandler))
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorizedRequest ->    
                                        authorizedRequest
                                            .requestMatchers(
                                                new AntPathRequestMatcher("/api/auth/**"),
                                                new AntPathRequestMatcher("/api/employees/save")
                                            ).permitAll()

                                            .requestMatchers(
                                                new AntPathRequestMatcher("/api/approvals/**")
                                            ).hasRole("MANAGER")
                                            
                                            .requestMatchers(
                                                new AntPathRequestMatcher("/api/employees/my-manager")
                                            ).hasRole("STAFF")

                                            .requestMatchers(
                                                new AntPathRequestMatcher("/api/employees/**")
                                            ).hasRole("ADMIN")

                                            .requestMatchers(
                                                new AntPathRequestMatcher("/api/reports/**")
                                            ).hasAnyRole("STAFF", "MANAGER", "ADMIN")

                                            .anyRequest().authenticated()
                                    );

        http.addFilterBefore(authJWTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
