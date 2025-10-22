package com.example.timesheetreport.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_m_approval_report")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "approvalReport", fetch = FetchType.LAZY)
    public List<Report> reports;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Employee manager;

    private Integer month_period;
    private Integer year_period;
    private String status;
    
    @Column(columnDefinition = "boolean default false")
    private Boolean is_deleted = false;
}
