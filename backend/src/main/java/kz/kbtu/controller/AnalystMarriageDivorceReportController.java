package kz.kbtu.controller;

import kz.kbtu.dto.report.PendingMarriageDivorceReportResponse;
import kz.kbtu.dto.report.RejectionRequest;
import kz.kbtu.entity.report.PendingMarriageDivorceReport;
import kz.kbtu.service.report.MarriageDivorceReportService;
import kz.kbtu.service.report.PendingMarriageDivorceReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/analyst/reports/marriage-divorce")
@PreAuthorize("hasAuthority('analyst')")
public class AnalystMarriageDivorceReportController {

    private final MarriageDivorceReportService marriageDivorceReportService;
    private final PendingMarriageDivorceReportService pendingMarriageDivorceReportService;

    @PostMapping("/{id}/approve")
    public ResponseEntity<String> approveReport(@PathVariable UUID id){
        marriageDivorceReportService.approveReport(id);
        return ResponseEntity.ok("Marriage-divorce report approved.");
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<String> rejectReport(@PathVariable UUID id, @RequestBody RejectionRequest request){
        marriageDivorceReportService.rejectReport(id, request.getRejectionReason());
        return ResponseEntity.ok("Report rejected and returned to partner with reason.");
    }

    @GetMapping("")
    public ResponseEntity<List<PendingMarriageDivorceReportResponse>> getPendingMarriageDivorceReports() {
        return ResponseEntity.ok(marriageDivorceReportService.getAllPendingReports());
    }
}
