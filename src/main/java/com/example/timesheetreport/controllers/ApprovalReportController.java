package com.example.timesheetreport.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.timesheetreport.controllers.response.ApiResponse;
import com.example.timesheetreport.controllers.response.Response;
import com.example.timesheetreport.models.DTO.ApprovalReportDTO;
import com.example.timesheetreport.models.DTO.ReportDTO;
import com.example.timesheetreport.security.JwtUtil;
import com.example.timesheetreport.services.ApprovalReportService;

@RestController
@RequestMapping("/api/reports/approval")
public class ApprovalReportController {
    private final ApprovalReportService approvalReportService;
    private final ApiResponse apiResponse;
    private final JwtUtil jwtUtils;

    @Autowired
    public ApprovalReportController(ApprovalReportService approvalReportService, ApiResponse apiResponse, JwtUtil jwtUtils) {
        this.approvalReportService = approvalReportService;
        this.apiResponse = apiResponse;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    public ResponseEntity<Response<List<ApprovalReportDTO>>> get(@RequestHeader(name = "Authorization") String token) {
        try {
            String bearerToken = token.replace("Bearer ", "");
            String username = jwtUtils.getUsernameFromToken(bearerToken);
            return apiResponse.success(200,"Berhasil menampilkan semua approval report", approvalReportService.get(username));
        } catch (Exception e) {
            return apiResponse.error(500, "Gagal menampilkan semua approval report", e.getMessage());
        }
    }

    @GetMapping(value = {"form", "form/{id}"})
    public ResponseEntity<Response<List<ReportDTO>>> get(@PathVariable(required = false, name = "id") Integer id, @RequestHeader(name = "Authorization") String token) {
        try {
            String bearerToken = token.replace("Bearer ", "");
            String username = jwtUtils.getUsernameFromToken(bearerToken);
            return apiResponse.success(200, "Berhasil menampilkan approval report", approvalReportService.get(id, username));
        } catch (Exception e) {
            return apiResponse.error(500, "Gagal menampilkan approval report", e.getMessage());
        }
    }
}
