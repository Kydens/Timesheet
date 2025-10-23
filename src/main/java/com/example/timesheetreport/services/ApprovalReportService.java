package com.example.timesheetreport.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.timesheetreport.models.ApprovalReport;
import com.example.timesheetreport.models.DTO.ApprovalReportDTO;
import com.example.timesheetreport.models.DTO.EmployeeDTO;
import com.example.timesheetreport.models.DTO.ReportDTO;
import com.example.timesheetreport.repositories.EmployeeRepository;
import com.example.timesheetreport.repositories.ApprovalReportRepository;

@Service
public class ApprovalReportService {
    private final ApprovalReportRepository approvalReportRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public ApprovalReportService(ApprovalReportRepository approvalReportRepository, EmployeeRepository employeeRepository) {
        this.approvalReportRepository = approvalReportRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<ApprovalReportDTO> get(String username) {
        return approvalReportRepository.getByManager(username);
    }

    public ApprovalReportDTO getId(Integer id, String username) {
        return approvalReportRepository.getIdByManager(username, id);
    }

    public List<ReportDTO> getReport(Integer id, String username) {
        return approvalReportRepository.getByManager(username,id);
    }

    public ApprovalReportDTO save(ApprovalReportDTO approvalReportDTO, String username) {
        ApprovalReport approvalReport = approvalReportRepository.findById(approvalReportDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Approval report not found"));

        if (approvalReportDTO.getStatus().equals("SUBMITTED")) {
            throw new IllegalStateException("Report already approved");
        }

        approvalReport.setStatus(approvalReportDTO.getStatus());
        approvalReportRepository.save(approvalReport);
        return getId(approvalReport.getId(), username);
    }

    public Boolean remove(Integer id, String username) {
        ApprovalReport approval = approvalReportRepository.findById(id).orElse(null);
        if (approval != null) {
            approval.setIs_deleted(true);
            approvalReportRepository.save(approval);
            return true;
        }

        return false;
    }
}
