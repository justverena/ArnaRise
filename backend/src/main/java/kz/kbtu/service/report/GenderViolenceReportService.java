package kz.kbtu.service.report;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import kz.kbtu.dto.report.PendingGenderViolenceReportResponse;
import kz.kbtu.entity.report.GenderViolenceReport;
import kz.kbtu.entity.report.PendingGenderViolenceReport;
import kz.kbtu.enums.ReportStatus;
import kz.kbtu.repository.GenderViolenceReportRepository;
import kz.kbtu.repository.PendingGenderViolenceReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenderViolenceReportService {

    private final PendingGenderViolenceReportRepository pendingRepository;
    private final GenderViolenceReportRepository reportRepository;

    public List<PendingGenderViolenceReportResponse> getAllPendingReports() {
        return pendingRepository.findAll().stream()
                .map(r -> PendingGenderViolenceReportResponse.builder()
                        .id(r.getId())
                        .date(r.getDate())
                        .gender(r.getGender())
                        .district(r.getDistrict())
                        .age(r.getAge())
                        .violenceType(r.getViolenceType())
                        .location(r.getLocation())
                        .timeOfDay(r.getTimeOfDay())
                        .socialStatus(r.getSocialStatus())
                        .aggressorRelation(r.getAggressorRelation())
                        .caseDescription(r.getCaseDescription())
                        .authority(r.getAuthority())
                        .status(r.getStatus())
                        .rejectionReason(r.getRejectionReason())
                        .actions(r.getActions())
                        .submittedBy(r.getSubmittedBy().getId())
                        .build())
                .toList();
    }

    @Transactional
    public void approveReport(UUID id){
        PendingGenderViolenceReport pending = pendingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pending report not found"));

        GenderViolenceReport report = new GenderViolenceReport();

        report.setDate(pending.getDate());
        report.setGender(pending.getGender());
        report.setDistrict(pending.getDistrict());
        report.setAge(pending.getAge());
        report.setViolenceType(pending.getViolenceType());
        report.setLocation(pending.getLocation());
        report.setTimeOfDay(pending.getTimeOfDay());
        report.setSocialStatus(pending.getSocialStatus());
        report.setAggressorRelation(pending.getAggressorRelation());
        report.setCaseDescription(pending.getCaseDescription());
        report.setAuthority(pending.getAuthority());
        report.setSubmittedBy(pending.getSubmittedBy());
        report.setActions(new ArrayList<>(pending.getActions()));
        report.setStatus(ReportStatus.APPROVED);
        reportRepository.save(report);
        pendingRepository.delete(pending);
    }

    @Transactional
    public void rejectReport(UUID id, String reason){
        PendingGenderViolenceReport pending = pendingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pending report not found"));

        pending.setStatus(ReportStatus.REJECTED);
        pending.setRejectionReason(reason);
        pendingRepository.save(pending);

    }

}
