//package kz.kbtu.service.report;
//
//import kz.kbtu.dto.report.GenderViolenceReportRequest;
//import kz.kbtu.entity.User;
//import kz.kbtu.entity.report.GenderViolenceReport;
//import kz.kbtu.repository.GenderViolenceReportRepository;
//import kz.kbtu.repository.UserRepository;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class GenderViolenceReportService {
//
//    private final GenderViolenceReportRepository reportRepository;
//    private final UserRepository userRepository;
//
//    public GenderViolenceReportService(GenderViolenceReportRepository reportRepository, UserRepository userRepository) {
//        this.reportRepository = reportRepository;
//        this.userRepository = userRepository;
//    }
//
//    public void submitReport(GenderViolenceReportRequest request, UserDetails userDetails) {
//        User user = userRepository.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        GenderViolenceReport report = GenderViolenceReport.createFromRequest(request, user);
//        reportRepository.save(report);
//    }
//
//}
