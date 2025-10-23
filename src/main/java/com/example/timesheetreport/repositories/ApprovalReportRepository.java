package com.example.timesheetreport.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.timesheetreport.models.ApprovalReport;
import com.example.timesheetreport.models.DTO.ApprovalReportDTO;
import com.example.timesheetreport.models.DTO.ReportDTO;

@Repository
public interface ApprovalReportRepository extends JpaRepository<ApprovalReport, Integer> {
    @Query("""
        SELECT 
            new com.example.timesheetreport.models.DTO.ApprovalReportDTO(
                ar.id, m.id, m.name, ar.month_period, ar.year_period, ar.status
            )
        FROM ApprovalReport ar
        JOIN ar.manager m
        JOIN m.user u
        WHERE ar.is_deleted = false AND u.username = ?1
    """)
    public List<ApprovalReportDTO> getByManager(String username);

    @Query("""
        SELECT 
            new com.example.timesheetreport.models.DTO.ApprovalReportDTO(
                ar.id, m.id, m.name, ar.month_period, ar.year_period, ar.status
            )
        FROM ApprovalReport ar
        JOIN ar.manager m
        JOIN m.user u
        WHERE ar.is_deleted = false AND u.username = ?1 AND ar.id = ?2
    """)
    public ApprovalReportDTO getIdByManager(String username, Integer id);

    @Query("""
        SELECT 
            new com.example.timesheetreport.models.DTO.ReportDTO(
                r.id, e.id, r.status, r.date, r.project_name, r.client_name, r.activity, r.hour, r.remarks, ar.id
            )
        FROM Report r
        JOIN r.employee e
        JOIN r.approvalReport ar
        JOIN ar.manager m
        JOIN m.user u
        WHERE r.is_deleted = false AND u.username = ?1 AND ar.id = ?2
    """)
    public List<ReportDTO> getByManager(String username, Integer id);

    @Query("""
        SELECT
            new com.example.timesheetreport.models.DTO.ApprovalReportDTO(
                ar.id, m.id, m.name, ar.month_period, ar.year_period, ar.status
            )
        FROM ApprovalReport ar
        JOIN ar.manager m
        WHERE ar.is_deleted = false AND m.id = ?1 AND ar.month_period = ?2 AND ar.year_period = ?3
    """)
    public ApprovalReportDTO findExistingApprovalReport(Integer managerId, Integer month, Integer year);
}
