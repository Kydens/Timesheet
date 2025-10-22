package com.example.timesheetreport.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.timesheetreport.models.User;
import com.example.timesheetreport.models.DTO.UserDTO;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("""
        SELECT
            new com.example.timesheetreport.models.DTO.UserDTO(
                u.id, r.id, u.username, u.password, u.email_company
            )
        FROM User u
        JOIN u.role r
        WHERE u.is_deleted = false
    """)
    public List<UserDTO> get();

    @Query("""
        SELECT
            new com.example.timesheetreport.models.DTO.UserDTO(
                u.id, r.id, u.username, u.password, u.email_company
            )
        FROM User u
        JOIN u.role r
        WHERE u.is_deleted = false AND u.id = ?1
    """)
    public UserDTO get(Integer id);

    User findByUsername(String username);
    Boolean existsByUsername(String username);
}
