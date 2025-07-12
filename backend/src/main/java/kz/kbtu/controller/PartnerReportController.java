package kz.kbtu.controller;


import kz.kbtu.dto.report.GenderViolenceReportRequest;
import kz.kbtu.dto.report.PendingGenderViolenceReportRequest;
import kz.kbtu.dto.report.PendingMarriageDivorceReportRequest;
import kz.kbtu.service.report.PendingGenderViolenceReportService;
import kz.kbtu.service.report.PendingMarriageDivorceReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @PreAuthorize("hasAuthority('partner')")
    @PostMapping("/gender-violence")
    public ResponseEntity<String> submitReport(@RequestBody PendingGenderViolenceReportRequest request, @AuthenticationPrincipal UserDetails userDetails){
        pendingGenderViolenceReportService.submitReport(request,userDetails);
        return ResponseEntity.ok("Report submitted successfully");
    }

    @PreAuthorize("hasAuthority('partner')")
    @PostMapping("/marriage-divorce")
    public ResponseEntity<String> submitReport(@RequestBody PendingMarriageDivorceReportRequest request, @AuthenticationPrincipal UserDetails userDetails){
        pendingMarriageDivorceReportService.submitReport(request,userDetails);
        return ResponseEntity.ok("Report submitted successfully");
    }



}
