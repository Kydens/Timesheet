package com.example.timesheetreport.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_m_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
    
    @Column(unique = true)
    private String username;
    private String password;

    @Column(unique = true)
    private String email_company;

    @Column(columnDefinition = "boolean default false")
    private Boolean is_deleted = false;
}
