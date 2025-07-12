package kz.kbtu.controller;

import kz.kbtu.dto.report.RejectionRequest;
import kz.kbtu.service.report.GenderViolenceReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/analyst/reports/gender-violence")
@PreAuthorize("hasAuthority('analyst')")
public class AnalystGenderViolenceReportController {

    private final GenderViolenceReportService genderViolenceReportService;

    @PostMapping("/{id}/approve")
    public ResponseEntity<String> approveReport(@PathVariable UUID id){
        genderViolenceReportService.approveReport(id);
        return ResponseEntity.ok("Report approved and saved to main table.");

    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<String> rejectReport(@PathVariable UUID id, @RequestBody RejectionRequest request) {
        genderViolenceReportService.rejectReport(id, request.getRejectionReason());
        return ResponseEntity.ok("Report rejected and returned to partner with reason.");
    }

}
