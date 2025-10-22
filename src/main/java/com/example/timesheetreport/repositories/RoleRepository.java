package com.example.timesheetreport.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.timesheetreport.models.Role;
import com.example.timesheetreport.models.DTO.RoleDTO;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("""
        SELECT
            new com.example.timesheetreport.models.DTO.RoleDTO(
                r.id, r.name
            )
        FROM Role r
        WHERE r.is_deleted = false
    """)
    public List<RoleDTO> get();

    @Query("""
        SELECT
            new com.example.timesheetreport.models.DTO.RoleDTO(
                r.id, r.name
            )
        FROM Role r
        WHERE r.is_deleted = false AND r.id = ?1
    """)
    public RoleDTO get(Integer id);
}
