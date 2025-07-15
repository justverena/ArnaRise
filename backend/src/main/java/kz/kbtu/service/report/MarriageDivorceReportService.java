package kz.kbtu.service.report;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import kz.kbtu.dto.report.PendingMarriageDivorceReportResponse;
import kz.kbtu.entity.report.MarriageDivorceReport;
import kz.kbtu.entity.report.PendingMarriageDivorceReport;
import kz.kbtu.enums.ReportStatus;
import kz.kbtu.repository.MarriageDivorceReportRepository;
import kz.kbtu.repository.PendingMarriageDivorceReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class MarriageDivorceReportService {


    private final PendingMarriageDivorceReportRepository pendingRepository;
    private final MarriageDivorceReportRepository reportRepository;

    public List<PendingMarriageDivorceReportResponse> getAllPendingReports() {
        return pendingRepository.findAll().stream()
                .map(r -> PendingMarriageDivorceReportResponse.builder()
                        .id(r.getId())
                        .reportYear(r.getReportYear())
                        .district(r.getDistrict().name())
                        .marriageCount(r.getMarriageCount())
                        .divorceCount(r.getDivorceCount())
                        .ratioDivorcesToMarriagePercent(r.getRatioDivorcesToMarriagePercent())
                        .averageAge(r.getAverageAge())
                        .source(r.getSource().name())
                        .submittedBy(r.getSubmittedBy().getId())
                        .status(r.getStatus())
                        .rejectionReason(r.getRejectionReason())
                        .build())
                .toList();
    }

    @Transactional
    public void approveReport(UUID id){
        PendingMarriageDivorceReport pending = pendingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pending report not found"));

        MarriageDivorceReport report = new MarriageDivorceReport();
        report.setReportYear(pending.getReportYear());
        report.setDistrict(pending.getDistrict());
        report.setMarriageCount(pending.getMarriageCount());
        report.setDivorceCount(pending.getDivorceCount());
        report.setRatioDivorcesToMarriagePercent(pending.getRatioDivorcesToMarriagePercent());
        report.setAverageAge(pending.getAverageAge());
        report.setSource(pending.getSource());
        report.setSubmittedBy(pending.getSubmittedBy());
        report.setStatus(ReportStatus.APPROVED);
        reportRepository.save(report);
        pendingRepository.delete(pending);
    }

    @Transactional
    public void rejectReport(UUID id, String reason){
        PendingMarriageDivorceReport pending = pendingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pending report not found"));

        pending.setStatus(ReportStatus.REJECTED);
        pending.setRejectionReason(reason);
        pendingRepository.save(pending);

    }

}
