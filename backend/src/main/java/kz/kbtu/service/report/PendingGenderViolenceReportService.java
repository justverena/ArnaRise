package kz.kbtu.service.report;

import kz.kbtu.dto.report.GenderViolenceReportRequest;
import kz.kbtu.dto.report.PendingGenderViolenceReportRequest;
import kz.kbtu.entity.User;
import kz.kbtu.entity.report.GenderViolenceReport;
import kz.kbtu.entity.report.PendingGenderViolenceReport;
import kz.kbtu.enums.ReportStatus;
import kz.kbtu.repository.GenderViolenceReportRepository;
import kz.kbtu.repository.PendingGenderViolenceReportRepository;
import kz.kbtu.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PendingGenderViolenceReportService {

    private final PendingGenderViolenceReportRepository pendingReportRepository;
    private final UserRepository userRepository;

    public PendingGenderViolenceReportService(PendingGenderViolenceReportRepository pendingReportRepository, UserRepository userRepository) {
        this.pendingReportRepository = pendingReportRepository;
        this.userRepository = userRepository;
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

}
