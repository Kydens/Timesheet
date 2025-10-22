package com.example.timesheetreport.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.timesheetreport.models.Division;
import com.example.timesheetreport.models.DTO.DivisionDTO;
import com.example.timesheetreport.repositories.DivisionRepository;

@Service
public class DivisionService {
    private final DivisionRepository divisionRepository;

    @Autowired
    public DivisionService(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    public List<DivisionDTO> get() {
        return divisionRepository.get();
    }
    
    public DivisionDTO get(Integer id) {
        return divisionRepository.get(id);
    }

    public DivisionDTO save(DivisionDTO divisionDTO) {
        Division division = new Division();

        division.setId(divisionDTO.getId());
        division.setName(divisionDTO.getName());

        Division divisionResult = divisionRepository.save(division);

        return get(divisionResult.getId());
    }

    public Boolean remove(Integer id) {
        Division division = divisionRepository.findById(id).orElse(null);
        
        if (division != null) {
            division.setIs_deleted(true);
            divisionRepository.save(division);
            return true;
        }

        return false;
    }
}
