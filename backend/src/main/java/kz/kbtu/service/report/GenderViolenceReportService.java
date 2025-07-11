package kz.kbtu.service.report;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import kz.kbtu.dto.report.GenderViolenceReportRequest;
import kz.kbtu.entity.User;
import kz.kbtu.entity.report.GenderViolenceReport;
import kz.kbtu.entity.report.PendingGenderViolenceReport;
import kz.kbtu.enums.ReportStatus;
import kz.kbtu.repository.GenderViolenceReportRepository;
import kz.kbtu.repository.PendingGenderViolenceReportRepository;
import kz.kbtu.repository.UserRepository;
import kz.kbtu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenderViolenceReportService {

    private final PendingGenderViolenceReportRepository pendingRepository;
    private final GenderViolenceReportRepository reportRepository;


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
