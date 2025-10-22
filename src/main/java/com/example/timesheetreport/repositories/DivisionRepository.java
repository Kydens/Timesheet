package com.example.timesheetreport.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.timesheetreport.models.Division;
import com.example.timesheetreport.models.DTO.DivisionDTO;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Integer> {
    @Query("""
        SELECT
            new com.example.timesheetreport.models.DTO.DivisionDTO(
                d.id, d.name
            )
        FROM Division d
        WHERE is_deleted = false
    """)
    public List<DivisionDTO> get();

    @Query("""
        SELECT
            new com.example.timesheetreport.models.DTO.DivisionDTO(
                d.id, d.name
            )
        FROM Division d
        WHERE is_deleted = false AND id = ?1
    """)
    public DivisionDTO get(Integer id);
}
