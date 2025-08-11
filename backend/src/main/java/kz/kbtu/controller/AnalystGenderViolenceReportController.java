package kz.kbtu.controller;

import kz.kbtu.dto.report.RejectionRequest;
import kz.kbtu.dto.report.PendingGenderViolenceReportResponse;
import kz.kbtu.dto.report.ReportShortResponse;
import kz.kbtu.entity.report.GenderViolenceReport;
import kz.kbtu.entity.report.PendingGenderViolenceReport;
import kz.kbtu.service.report.GenderViolenceReportService;
import kz.kbtu.service.report.PendingGenderViolenceReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/analyst/reports/gender-violence")
@PreAuthorize("hasAuthority('analyst')")
public class AnalystGenderViolenceReportController {

    private final GenderViolenceReportService genderViolenceReportService;
    private final PendingGenderViolenceReportService pendingGenderViolenceReportService;
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

    @GetMapping("")
    public ResponseEntity<List<ReportShortResponse>> getAllPendingGenderViolenceReports() {
        return ResponseEntity.ok(genderViolenceReportService.getAllPendingReports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PendingGenderViolenceReportResponse> getPendingGenderViolenceReport(@PathVariable UUID id) {
        return ResponseEntity.ok(genderViolenceReportService.getPendingReportById(id));
    }
}
