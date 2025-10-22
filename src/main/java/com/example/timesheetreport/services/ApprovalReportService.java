package com.example.timesheetreport.services;

import java.util.List;

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

    public List<ReportDTO> get(Integer id, String username) {
        return approvalReportRepository.getByManager(username,id);
    }

    public List<ReportDTO> save(ApprovalReportDTO approvalReportDTO, String username) {
        ApprovalReport approvalReport = new ApprovalReport();

        EmployeeDTO empDTO = employeeRepository.getEmployeeByUsername(username);

        approvalReport.setId(approvalReportDTO.getId());
        approvalReport.setManager(employeeRepository.findById(empDTO.getManagerId()).orElseThrow(() -> new RuntimeException("Manager not found")));
        approvalReport.setMonth_period(approvalReportDTO.getMonthPeriod());
        approvalReport.setMonth_period(approvalReportDTO.getYearPeriod());
        approvalReport.setStatus(approvalReportDTO.getStatus());

        ApprovalReport resultReport = approvalReportRepository.save(approvalReport);

        return get(resultReport.getId(), username);
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
