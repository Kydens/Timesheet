package com.example.timesheetreport.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUserDTO {
    private EmployeeDTO employeeDTO;
    private UserDTO userDTO;
}
