package com.example.timesheetreport.models;

import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_m_employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY)
    public User user;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    public List<Report> reports;

    @ManyToOne
    @JoinColumn(name = "division_id", referencedColumnName = "id")
    private Division division;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    public List<Employee> subordinates;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Employee manager;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    public List<ApprovalReport> approvalReports;

    private String name;
    private String address;

    @Column(unique = true)
    private String phone_number;
    private Date date_birth;

    @Column(columnDefinition = "boolean default false")
    private Boolean is_deleted = false;
}
