package com.example.timesheetreport.services;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.timesheetreport.models.ApprovalReport;
import com.example.timesheetreport.models.Employee;
import com.example.timesheetreport.models.Report;
import com.example.timesheetreport.models.DTO.ApprovalReportDTO;
import com.example.timesheetreport.models.DTO.EmployeeDTO;
import com.example.timesheetreport.models.DTO.ReportDTO;
import com.example.timesheetreport.repositories.ApprovalReportRepository;
import com.example.timesheetreport.repositories.EmployeeRepository;
import com.example.timesheetreport.repositories.ReportRepository;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final EmployeeRepository employeeRepository;
    private final ApprovalReportRepository approvalReportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository, EmployeeRepository employeeRepository, ApprovalReportRepository approvalReportRepository) {
        this.reportRepository = reportRepository;
        this.employeeRepository = employeeRepository;
        this.approvalReportRepository = approvalReportRepository;
    }

    public List<ReportDTO> get(String username) {
        return reportRepository.get(username);
    }

    public ReportDTO get(Integer id, String username) {
        return reportRepository.get(id, username);
    }

    public ReportDTO save(ReportDTO reportDTO, String username) {
        Report report = new Report();

        EmployeeDTO empDTO = employeeRepository.getEmployeeByUsername(username);

        report.setId(reportDTO.getId());
        report.setEmployee(employeeRepository.findById(empDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Employee not found")));
        report.setStatus(reportDTO.getStatus());
        report.setDate(reportDTO.getDate());
        report.setProject_name(reportDTO.getProjectName());
        report.setClient_name(reportDTO.getClientName());
        report.setActivity(reportDTO.getActivity());
        report.setHour(reportDTO.getHour());
        report.setRemarks(reportDTO.getRemarks());

        // approval report
        Employee manager = employeeRepository.findById(empDTO.getManagerId()).orElseThrow(() -> new EntityNotFoundException("Manager not found"));

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"));
        cal.setTime(report.getDate());
        Integer month = cal.get(Calendar.MONTH) + 1;
        Integer year = cal.get(Calendar.YEAR);

        ApprovalReportDTO approvalReportDTO = approvalReportRepository.findExistingApprovalReport(manager.getId(), month, year);

        ApprovalReport approvalReport;

        if (approvalReportDTO == null) {
            ApprovalReport approval = new ApprovalReport();
            approval.setManager(manager);
            approval.setMonth_period(month);
            approval.setYear_period(year);
            approval.setStatus("DRAFT");
            approvalReport = approvalReportRepository.save(approval);
        } else {
            approvalReport = approvalReportRepository.findById(approvalReportDTO.getId()).orElse(null);
        }

        report.setApprovalReport(approvalReport);

        // save
        Report resultReport = reportRepository.save(report);

        return get(resultReport.getId(), resultReport.getEmployee().getUser().getUsername());
    }

    public Boolean remove(Integer id, String username) {
        Report report = reportRepository.findById(id).orElse(null);

        if (report != null) {
            report.setIs_deleted(true);
            reportRepository.save(report);
            return true;
        }

        return false;
    }
}
