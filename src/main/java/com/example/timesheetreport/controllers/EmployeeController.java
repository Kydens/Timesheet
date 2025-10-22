package com.example.timesheetreport.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.timesheetreport.controllers.response.ApiResponse;
import com.example.timesheetreport.controllers.response.Response;
import com.example.timesheetreport.models.DTO.EmployeeDTO;
import com.example.timesheetreport.models.DTO.EmployeeUserDTO;
import com.example.timesheetreport.security.JwtUtil;
import com.example.timesheetreport.services.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ApiResponse apiResponse;
    private final JwtUtil jwtUtils;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ApiResponse apiResponse, JwtUtil jwtUtils) {
        this.employeeService = employeeService;
        this.apiResponse = apiResponse;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("my-manager")
    public ResponseEntity<Response<String>> getManager(@RequestHeader(name = "Authorization") String token) {
        try {
            String bearerToken = token.replace("Bearer ", "");
            String username = jwtUtils.getUsernameFromToken(bearerToken);
            return apiResponse.success(200, "Berhasil menampilkan semua employee", employeeService.getManager(username));
        } catch (Exception e) {
            return apiResponse.error(500, "Gagal menampilkan semua employee", e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Response<List<EmployeeDTO>>> get() {
        try {
            return apiResponse.success(200, "Berhasil menampilkan semua employee", employeeService.get());
        } catch (Exception e) {
            return apiResponse.error(500, "Gagal menampilkan semua employee", e.getMessage());
        }
    }

    @GetMapping(value = {"form", "form/{id}"})
    public ResponseEntity<Response<EmployeeDTO>> get(@PathVariable(required = false, name = "id") Integer id) {
        try {
            if (id != null) {
                return apiResponse.success(200, "Berhasil menampilkan employee", employeeService.get(id));
            }

            return apiResponse.success(200, "", new EmployeeDTO());
        } catch (Exception e) {
            return apiResponse.error(500, "Gagal menampilkan employee", e.getMessage());
        }
    }

    @PostMapping("save")
    public ResponseEntity<Response<EmployeeDTO>> save(@RequestBody EmployeeUserDTO employeeUserDTO) {
        try {
            return apiResponse.success(201, "Berhasil menambahkan/mengupdate data", employeeService.save(employeeUserDTO.getEmployeeDTO(), employeeUserDTO.getUserDTO()));
        } catch (Exception e) {
            return apiResponse.error(400, "Gagal menambahkan/mengupdate data", e.getMessage());
        }
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity<Response<Boolean>> remove(@PathVariable(name = "id") Integer id) {
        try {
            return apiResponse.success(201, "Berhasil menghapus data", employeeService.remove(id));
        } catch (Exception e) {
            return apiResponse.error(500, "Gagal menghapus data", e.getMessage());
        }
    }
}
