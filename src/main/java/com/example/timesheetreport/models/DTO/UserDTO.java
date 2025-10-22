package com.example.timesheetreport.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private Integer roleId;
    private String username;
    private String password;
    private String emailCompany;
    private Boolean isDeleted = false;


    public UserDTO(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    // untuk login
    public UserDTO(Integer id, Integer roleId, String username, String password, Boolean isDeleted) {
        this.id = id;
        this.roleId = roleId;
        this.username = username;
        this.password = password;
        this.isDeleted = isDeleted;
    }

    public UserDTO(Integer id, Integer roleId, String username, String password, String emailCompany) {
        this.id = id;
        this.roleId = roleId;
        this.username = username;
        this.password = password;
        this.emailCompany = emailCompany;
    }
}
