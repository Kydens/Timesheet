package com.example.timesheetreport.models.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private Integer id;
    private Integer employeeId;
    private String status;
    private Date date;
    private String projectName;
    private String clientName;
    private String activity;
    private Integer hour;
    private String remarks;
    private Integer approvalId;
    private Boolean isDeleted = false;

    public ReportDTO(Integer id, Integer employeeId, String status, Date date, String projectName, String clientName, String activity, Integer hour, String remarks) {
        this.id = id;
        this.employeeId = employeeId;
        this.status = status;
        this.date = date;
        this.projectName = projectName;
        this.clientName = clientName;
        this.activity = activity;
        this.hour = hour;
        this.remarks = remarks;
    }

    public ReportDTO(Integer id, Integer employeeId, String status, Date date, String projectName, String clientName, String activity, Integer hour, String remarks, Integer approvalId) {
        this.id = id;
        this.employeeId = employeeId;
        this.status = status;
        this.date = date;
        this.projectName = projectName;
        this.clientName = clientName;
        this.activity = activity;
        this.hour = hour;
        this.remarks = remarks;
        this.approvalId = approvalId;
    }
}
