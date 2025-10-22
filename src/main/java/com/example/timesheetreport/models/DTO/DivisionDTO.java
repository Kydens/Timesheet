package com.example.timesheetreport.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DivisionDTO {
    private Integer id;
    private String name;
    private Boolean isDeleted = false;

    public DivisionDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
