package com.example.timesheetreport.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.timesheetreport.models.Report;
import com.example.timesheetreport.models.DTO.ReportDTO;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Query("""
        SELECT
            new com.example.timesheetreport.models.DTO.ReportDTO(
                r.id, e.id, r.status, r.date, r.project_name, r.client_name, r.activity, r.hour, r.remarks 
            )
        FROM Report r
        JOIN r.employee e
        JOIN e.user u
        WHERE r.is_deleted = false AND u.username = ?1
    """)
    public List<ReportDTO> get(String username);

    @Query("""
        SELECT
            new com.example.timesheetreport.models.DTO.ReportDTO(
                r.id, e.id, r.status, r.date, r.project_name, r.client_name, r.activity, r.hour, r.remarks 
            )
        FROM Report r
        JOIN r.employee e
        JOIN e.user u
        WHERE r.is_deleted = false AND u.username = ?2 AND r.id = ?1
    """)
    public ReportDTO get(Integer id, String username);
}
