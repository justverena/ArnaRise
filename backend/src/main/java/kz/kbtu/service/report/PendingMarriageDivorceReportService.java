package kz.kbtu.service.report;

import kz.kbtu.dto.report.PendingMarriageDivorceReportRequest;
import kz.kbtu.entity.User;
import kz.kbtu.entity.report.PendingMarriageDivorceReport;
import kz.kbtu.enums.ReportStatus;
import kz.kbtu.repository.PendingMarriageDivorceReportRepository;
import kz.kbtu.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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
}
