package com.example.timesheetreport.models.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Integer id;
    private Integer divisionId;
    private Integer managerId;
    private String managerName;
    private String name;
    private String address;
    private String phoneNumber;
    private Date dateBirth;
    private Boolean isDeleted = false;

    public EmployeeDTO(Integer id) {
        this.id = id;
    }

    public EmployeeDTO(Integer id, Integer divisionId, Integer managerId, String name, String address, String phoneNumber, Date dateBirth) {
        this.id = id;
        this.divisionId = divisionId;
        this.managerId = managerId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.dateBirth = dateBirth;
    }

    public EmployeeDTO(Integer id, Integer divisionId, Integer managerId, String managerName, String name, String address, String phoneNumber, Date dateBirth) {
        this.id = id;
        this.divisionId = divisionId;
        this.managerId = managerId;
        this.managerName = managerName;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.dateBirth = dateBirth;
    }
}
