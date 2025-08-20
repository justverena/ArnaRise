package kz.kbtu.service.report;

import kz.kbtu.dto.report.PendingMarriageDivorceReportRequest;
import kz.kbtu.entity.User;
import kz.kbtu.entity.report.PendingMarriageDivorceReport;
import kz.kbtu.enums.ReportStatus;
import kz.kbtu.repository.PendingMarriageDivorceReportRepository;
import kz.kbtu.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
public class PendingMarriageDivorceReportService {

    private final PendingMarriageDivorceReportRepository pendingMarriageDivorceReportRepository;
    private final UserRepository userRepository;

    public PendingMarriageDivorceReportService(PendingMarriageDivorceReportRepository pendingMarriageDivorceReportRepository, UserRepository userRepository) {
        this.pendingMarriageDivorceReportRepository = pendingMarriageDivorceReportRepository;
        this.userRepository = userRepository;
    }
    private BigDecimal calculateRatio(int marriage, int divorce) {
        if (marriage == 0) return BigDecimal.ZERO;
        return BigDecimal.valueOf(divorce * 100.0 / marriage).setScale(2, RoundingMode.HALF_UP);
    }
    public void submitReport(PendingMarriageDivorceReportRequest request, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        PendingMarriageDivorceReport report = new PendingMarriageDivorceReport();
        report.setReportYear(request.getReportYear());
        report.setDistrict(request.getDistrict());
        report.setMarriageCount(request.getMarriageCount());
        report.setDivorceCount(request.getDivorceCount());
        report.setRatioDivorcesToMarriagePercent(calculateRatio(request.getMarriageCount(), request.getDivorceCount()));
        report.setAverageAge(request.getAverageAge());
        report.setSource(request.getSource());
        report.setRejectionReason(request.getRejectionReason());
        report.setSubmittedBy(user);
        report.setStatus(request.getStatus());

        pendingMarriageDivorceReportRepository.save(report);
    }
    public List<PendingMarriageDivorceReport> getRejectedMarriageDivorceReports(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userDetails.getUsername()));

        return pendingMarriageDivorceReportRepository.findBySubmittedByAndStatus(user, ReportStatus.REJECTED);
    }

    public void updateRejectedMarriageDivorceReport(UUID reportId, PendingMarriageDivorceReportRequest request, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userDetails.getUsername()));

        PendingMarriageDivorceReport report = pendingMarriageDivorceReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        if (!report.getSubmittedBy().getId().equals(user.getId())) {
            throw new AccessDeniedException("User not authorized to update this report");
        }

        report.setReportYear(request.getReportYear());
        report.setDistrict(request.getDistrict());
        report.setMarriageCount(request.getMarriageCount());
        report.setDivorceCount(request.getDivorceCount());
        report.setRatioDivorcesToMarriagePercent(calculateRatio(request.getMarriageCount(), request.getDivorceCount()));
        report.setAverageAge(request.getAverageAge());
        report.setSource(request.getSource());
        report.setStatus(ReportStatus.PENDING);
        report.setRejectionReason(null);

        pendingMarriageDivorceReportRepository.save(report);
    }
}
