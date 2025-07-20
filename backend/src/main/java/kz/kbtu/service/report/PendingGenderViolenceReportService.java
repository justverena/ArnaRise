package kz.kbtu.service.report;

import kz.kbtu.dto.report.PendingGenderViolenceReportRequest;
import kz.kbtu.dto.report.PendingGenderViolenceReportResponse;
import kz.kbtu.entity.User;
import kz.kbtu.entity.report.PendingGenderViolenceReport;
import kz.kbtu.enums.ReportStatus;
import kz.kbtu.repository.PendingGenderViolenceReportRepository;
import kz.kbtu.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PendingGenderViolenceReportService {

    private final PendingGenderViolenceReportRepository pendingReportRepository;
    private final UserRepository userRepository;
    private final PendingGenderViolenceReportRepository pendingGenderViolenceReportRepository;

    public PendingGenderViolenceReportService(PendingGenderViolenceReportRepository pendingReportRepository, UserRepository userRepository, PendingGenderViolenceReportRepository pendingGenderViolenceReportRepository) {
        this.pendingReportRepository = pendingReportRepository;
        this.userRepository = userRepository;
        this.pendingGenderViolenceReportRepository = pendingGenderViolenceReportRepository;
    }

    public void submitReport(PendingGenderViolenceReportRequest request, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        PendingGenderViolenceReport report = new PendingGenderViolenceReport();
        report.setDate(request.getDate());
        report.setGender(request.getGender());
        report.setDistrict(request.getDistrict());
        report.setAge(request.getAge());
        report.setSocialStatus(request.getSocialStatus());

        report.setViolenceType(request.getViolenceType());
        report.setLocation(request.getLocation());
        report.setTimeOfDay(request.getTimeOfDay());
        report.setSocialStatus(request.getSocialStatus());
        report.setAggressorRelation(request.getAggressorRelation());
        report.setCaseDescription(request.getCaseDescription());
        report.setAuthority(request.getAuthority());
        report.setRejectionReason(request.getRejectionReason());
        report.setActions(request.getActions());
        report.setSubmittedBy(user);
        pendingReportRepository.save(report);
    }

    public List<PendingGenderViolenceReportResponse> getRejectedGenderViolenceReports(UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<PendingGenderViolenceReport> rejectedReports = pendingGenderViolenceReportRepository.findBySubmittedByAndStatus(user, ReportStatus.REJECTED);
        return rejectedReports.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PendingGenderViolenceReportResponse mapToResponse(PendingGenderViolenceReport report) {
        return new PendingGenderViolenceReportResponse(
                report.getId(),
                report.getDate(),
                report.getGender(),
                report.getDistrict(),
                report.getAge(),
                report.getViolenceType(),
                report.getLocation(),
                report.getTimeOfDay(),
                report.getSocialStatus(),
                report.getAggressorRelation(),
                report.getCaseDescription(),
                report.getActions(),
                report.getAuthority(),
                report.getActions(),
                report.getStatus(),
                report.getRejectionReason()
        );


    }

    public void updateRejectedGenderViolenceReport(UUID id, PendingGenderViolenceReportRequest request, UserDetails userDetails) {
        UUID partnerId = UUID.fromString(userDetails.getUsername());
        PendingGenderViolenceReport report = pendingGenderViolenceReportRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found"));

            if (!report.getSubmittedBy().getId().equals(partnerId)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update this report");
            }

            if (report.getStatus() != ReportStatus.REJECTED) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only rejected reports can be updated");
            }

            report.setDate(request.getDate());
            report.setGender(request.getGender());
            report.setDistrict(request.getDistrict());
            report.setAge(request.getAge());
            report.setViolenceType(request.getViolenceType());
            report.setLocation(request.getLocation());
            report.setTimeOfDay(request.getTimeOfDay());
            report.setSocialStatus(request.getSocialStatus());
            report.setAggressorRelation(request.getAggressorRelation());
            report.setCaseDescription(request.getCaseDescription());
            report.setActions(request.getActions());
            report.setAuthority(request.getAuthority());

            report.setStatus(ReportStatus.PENDING);
            report.setRejectionReason(null);
            report.setUpdatedAt(LocalDateTime.now());

            pendingGenderViolenceReportRepository.save(report);
        }



}
