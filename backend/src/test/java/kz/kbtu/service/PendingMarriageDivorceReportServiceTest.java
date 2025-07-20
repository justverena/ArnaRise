package kz.kbtu.service;

import kz.kbtu.dto.report.PendingMarriageDivorceReportRequest;
import kz.kbtu.entity.User;
import kz.kbtu.entity.report.PendingMarriageDivorceReport;
import kz.kbtu.enums.*;
import kz.kbtu.repository.PendingMarriageDivorceReportRepository;
import kz.kbtu.repository.UserRepository;
import kz.kbtu.service.report.PendingMarriageDivorceReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PendingMarriageDivorceReportServiceTest {

    @Mock
    private PendingMarriageDivorceReportRepository reportRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks
    private PendingMarriageDivorceReportService service;

    @Test
    void createReport_shouldSaveSuccessfully() {
        PendingMarriageDivorceReportRequest request = PendingMarriageDivorceReportRequest.builder()
                .reportYear(ReportYear.Y2015)
                .district(District.ALATAU)
                .marriageCount(34)
                .divorceCount(40)
                .averageAge(BigDecimal.valueOf(29.5))
                .source(Source.CIVIL_REGISTRY)
                .status(ReportStatus.PENDING)
                .rejectionReason("")
                .build();

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("partner@example.com");
        User user = new User();
        user.setEmail("partner@example.com");

        when(userRepository.findByEmail("partner@example.com")).thenReturn(Optional.of(user));

        service.submitReport(request, userDetails);
        ArgumentCaptor<PendingMarriageDivorceReport> captor = ArgumentCaptor.forClass(PendingMarriageDivorceReport.class);
        verify(reportRepository, times(1)).save(captor.capture());

        PendingMarriageDivorceReport saved = captor.getValue();
        assertEquals(Source.CIVIL_REGISTRY, saved.getSource());
        assertEquals(District.ALATAU, saved.getDistrict());
    }
    @Test
    void getRejectedMarriageDivorceReports_shouldReturnListOfReports() {
        UUID userId = UUID.randomUUID();
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(userId.toString(), "", List.of());

        User user = new User();
        user.setId(userId);

        PendingMarriageDivorceReport report = new PendingMarriageDivorceReport();
        report.setId(UUID.randomUUID());
        report.setSubmittedBy(user);
        report.setStatus(ReportStatus.REJECTED);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reportRepository.findBySubmittedByAndStatus(user, ReportStatus.REJECTED))
                .thenReturn(List.of(report));

        List<PendingMarriageDivorceReport> result = service.getRejectedMarriageDivorceReports(userDetails);

        assertEquals(1, result.size());
        assertEquals(ReportStatus.REJECTED, result.get(0).getStatus());
    }
    @Test
    void updateRejectedMarriageDivorceReport_shouldUpdateSuccessfully() {
        UUID reportId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(userId.toString(), "", List.of());

        User user = new User();
        user.setId(userId);

        PendingMarriageDivorceReport report = new PendingMarriageDivorceReport();
        report.setId(reportId);
        report.setSubmittedBy(user);
        report.setStatus(ReportStatus.REJECTED);

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        PendingMarriageDivorceReportRequest request = PendingMarriageDivorceReportRequest.builder()
                .reportYear(ReportYear.Y2020)
                .district(District.TURKSIB)
                .marriageCount(100)
                .divorceCount(30)
                .averageAge(BigDecimal.valueOf(28.2))
                .source(Source.CIVIL_REGISTRY)
                .build();

        service.updateRejectedMarriageDivorceReport(reportId, request, userDetails);

        verify(reportRepository).save(report);
        assertEquals(ReportStatus.PENDING, report.getStatus());
        assertNull(report.getRejectionReason());
        assertEquals(ReportYear.Y2020, report.getReportYear());
        assertEquals(District.TURKSIB, report.getDistrict());
    }
    @Test
    void updateRejectedMarriageDivorceReport_shouldThrowIfNotFound() {
        UUID reportId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(userId.toString(), "", List.of());

        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                service.updateRejectedMarriageDivorceReport(reportId, new PendingMarriageDivorceReportRequest(), userDetails));
    }
}
