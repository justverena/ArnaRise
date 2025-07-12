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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
