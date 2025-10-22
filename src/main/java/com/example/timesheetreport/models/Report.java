package com.example.timesheetreport.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_m_report")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    private String status;
    private Date date;
    private String project_name;
    private String client_name;
    private String activity;
    private Integer hour;
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "approval_id", referencedColumnName = "id")
    private ApprovalReport approvalReport;
    
    @Column(columnDefinition = "boolean default false")
    private Boolean is_deleted = false;
}
