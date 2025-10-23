package com.example.timesheetreport.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalReportDTO {
    private Integer id;
    private Integer managerId;
    private String managerName;
    private Integer monthPeriod;
    private Integer yearPeriod;
    private String status;
    private Boolean isDeleted = false;

    public ApprovalReportDTO(String status) {
        this.status = status;
    }
    
    public ApprovalReportDTO(Integer id, Integer managerId, String managerName, Integer monthPeriod, Integer yearPeriod, String status) {
        this.id = id;
        this.managerId = managerId;
        this.managerName = managerName;
        this.monthPeriod = monthPeriod;
        this.yearPeriod = yearPeriod;
        this.status = status;
    }
}
