package kz.kbtu.controller;


import kz.kbtu.dto.report.GenderViolenceReportRequest;
import kz.kbtu.dto.report.PendingGenderViolenceReportRequest;
import kz.kbtu.dto.report.PendingGenderViolenceReportResponse;
import kz.kbtu.dto.report.PendingMarriageDivorceReportRequest;
import kz.kbtu.entity.report.PendingGenderViolenceReport;
import kz.kbtu.entity.report.PendingMarriageDivorceReport;
import kz.kbtu.service.report.PendingGenderViolenceReportService;
import kz.kbtu.service.report.PendingMarriageDivorceReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/partner/reports")
@PreAuthorize("hasAuthority('partner')")
public class PartnerReportController {
    private final PendingGenderViolenceReportService pendingGenderViolenceReportService;
    private final PendingMarriageDivorceReportService pendingMarriageDivorceReportService;

    public PartnerReportController(PendingGenderViolenceReportService pendingGenderViolenceReportService, PendingMarriageDivorceReportService pendingMarriageDivorceReportService) {
        this.pendingGenderViolenceReportService = pendingGenderViolenceReportService;
        this.pendingMarriageDivorceReportService = pendingMarriageDivorceReportService;
    }


    @PostMapping("/gender-violence")
    public ResponseEntity<String> submitReport(@RequestBody PendingGenderViolenceReportRequest request, @AuthenticationPrincipal UserDetails userDetails){
        pendingGenderViolenceReportService.submitReport(request,userDetails);
        return ResponseEntity.ok("Report submitted successfully");
    }

    @PostMapping("/marriage-divorce")
    public ResponseEntity<String> submitReport(@RequestBody PendingMarriageDivorceReportRequest request, @AuthenticationPrincipal UserDetails userDetails){
        pendingMarriageDivorceReportService.submitReport(request,userDetails);
        return ResponseEntity.ok("Report submitted successfully");
    }

    @GetMapping("/gender-violence/rejected")
    public ResponseEntity<List<PendingGenderViolenceReportResponse>> getRejectedGenderViolenceReports(@AuthenticationPrincipal UserDetails userDetails){
        List<PendingGenderViolenceReportResponse> reports = pendingGenderViolenceReportService.getRejectedGenderViolenceReports(userDetails);
        return ResponseEntity.ok(reports);
    }
    @PatchMapping("/gender-violence/{id}")
    public ResponseEntity<String> updateRejectedGenderViolenceReport(@PathVariable UUID id, @RequestBody PendingGenderViolenceReportRequest request, @AuthenticationPrincipal UserDetails userDetails){
        pendingGenderViolenceReportService.updateRejectedGenderViolenceReport(id, request, userDetails);
        return ResponseEntity.ok("Report updated and resubmitted");
    }
    @GetMapping("/marriage-divorce/rejected")
    public ResponseEntity<List<PendingMarriageDivorceReport>> getRejectedMarriageDivorceReports(@AuthenticationPrincipal UserDetails userDetails) {
        List<PendingMarriageDivorceReport> reports = pendingMarriageDivorceReportService.getRejectedMarriageDivorceReports(userDetails);
        return ResponseEntity.ok(reports);
    }

    @PatchMapping("/marriage-divorce/{id}")
    public ResponseEntity<String> updateRejectedMarriageDivorceReport(@PathVariable UUID id,
                                                                      @RequestBody PendingMarriageDivorceReportRequest request,
                                                                      @AuthenticationPrincipal UserDetails userDetails) {

        pendingMarriageDivorceReportService.updateRejectedMarriageDivorceReport(id, request, userDetails);
        return ResponseEntity.ok("Report updated and resubmitted");
    }

}
