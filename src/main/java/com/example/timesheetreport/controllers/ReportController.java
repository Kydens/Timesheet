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
import com.example.timesheetreport.models.DTO.ReportDTO;
import com.example.timesheetreport.security.JwtUtil;
import com.example.timesheetreport.services.ReportService;

@RestController
@RequestMapping("api/reports")
public class ReportController {
    private final ReportService reportService;
    private final ApiResponse apiResponse;
    private final JwtUtil jwtUtils;

    @Autowired
    public ReportController(ReportService reportService, ApiResponse apiResponse, JwtUtil jwtUtils) {
        this.reportService = reportService;
        this.apiResponse = apiResponse;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    public ResponseEntity<Response<List<ReportDTO>>> get(@RequestHeader(name = "Authorization") String token) {
        try {
            String bearerToken = token.replace("Bearer ", "");
            String username = jwtUtils.getUsernameFromToken(bearerToken);
            return apiResponse.success(200, "Berhasil menampilkan semua report", reportService.get(username));
        } catch (Exception e) {
            return apiResponse.error(500, "Gagal menampilkan semua report", e.getMessage());
        }
    }

    @GetMapping(value = {"form", "form/{id}"})
    public ResponseEntity<Response<ReportDTO>> get(@PathVariable(required = false, name = "id") Integer id, @RequestHeader(required = false, name = "Authorization") String token) {
        try {
            if (id != null) {    
                String bearerToken = token.replace("Bearer ", "");
                String username = jwtUtils.getUsernameFromToken(bearerToken);
                return apiResponse.success(200, "Berhasil menampilkan report", reportService.get(id, username));
            }
            
            return apiResponse.success(200, "Berhasil menampilkan report", new ReportDTO());
        } catch (Exception e) {
            return apiResponse.error(500, "Gagal menampilkan report", e.getMessage());
        }
    }

    @PostMapping("save")
    public ResponseEntity<Response<ReportDTO>> save(@RequestBody ReportDTO reportDTO,  @RequestHeader(required = false, name = "Authorization") String token) {
        Integer statusCodeSuccess = 200;

        try {
            String bearerToken = token.replace("Bearer ", "");
            String username = jwtUtils.getUsernameFromToken(bearerToken);

            if (reportDTO.getId() != null) {
                statusCodeSuccess = 201;
            }

            return apiResponse.success(statusCodeSuccess, "Berhasil menambahkan/mengupdate report", reportService.save(reportDTO, username));
        } catch (Exception e) {
            return apiResponse.error(400, "Gagal menambahkan/mengupdate report", e.getMessage());
        }
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity<Response<Boolean>> remove(@PathVariable(name = "id") Integer id, @RequestHeader(name = "Authorization") String token) {
        try {
            String bearerToken = token.replace("Bearer ", "");
            String username = jwtUtils.getUsernameFromToken(bearerToken);
            return apiResponse.success(200, "Berhasil menghapus report", reportService.remove(id, username));
        } catch (Exception e) {
            return apiResponse.error(500, "Gagal menghapus report", e.getMessage());
        }
    }
}
