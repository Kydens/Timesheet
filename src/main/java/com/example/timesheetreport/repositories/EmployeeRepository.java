package com.example.timesheetreport.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.timesheetreport.models.Employee;
import com.example.timesheetreport.models.DTO.EmployeeDTO;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("""
        SELECT
            new com.example.timesheetreport.models.DTO.EmployeeDTO(
                e.id, d.id, m.id, e.name, e.address, e.phone_number, e.date_birth
            )
        FROM Employee e
        JOIN e.division d
        LEFT JOIN e.manager m
        WHERE e.is_deleted = false
    """)
    public List<EmployeeDTO> get();

    @Query("""
        SELECT
            new com.example.timesheetreport.models.DTO.EmployeeDTO(
                e.id, d.id, m.id, e.name, e.address, e.phone_number, e.date_birth
            )
        FROM Employee e
        JOIN e.division d
        LEFT JOIN e.manager m
        WHERE e.is_deleted = false AND e.id = ?1
    """)
    public EmployeeDTO get(Integer id);
    
     @Query("""
        SELECT
            new com.example.timesheetreport.models.DTO.EmployeeDTO(
                e.id, d.id, m.id, m.name, e.name, e.address, e.phone_number, e.date_birth
            )
        FROM Employee e
        JOIN e.user u
        JOIN e.division d
        LEFT JOIN e.manager m
        WHERE e.is_deleted = false AND u.username = ?1
    """)
    public EmployeeDTO getEmployeeByUsername(String username);
}
