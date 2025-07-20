package kz.kbtu.service;

import kz.kbtu.dto.report.PendingGenderViolenceReportRequest;
import kz.kbtu.dto.report.PendingGenderViolenceReportResponse;
import kz.kbtu.entity.User;
import kz.kbtu.entity.report.PendingGenderViolenceReport;
import kz.kbtu.enums.*;
import kz.kbtu.repository.PendingGenderViolenceReportRepository;
import kz.kbtu.repository.UserRepository;
import kz.kbtu.service.report.PendingGenderViolenceReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PendingGenderViolenceReportServiceTest {
    @Mock private PendingGenderViolenceReportRepository reportRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private PendingGenderViolenceReportService service;

    @Test
    void createReport_shouldSaveSuccessfully() {
        PendingGenderViolenceReportRequest request = PendingGenderViolenceReportRequest.builder()
                .gender(Gender.FEMALE)
                .date(LocalDate.now())
                .district(District.ALATAU)
                .age(30)
                .violenceType(ViolenceType.PHYSICAL)
                .location(LocationType.HOME)
                .timeOfDay(TimeOfDay.EVENING)
                .socialStatus(SocialStatus.EMPLOYED)
                .aggressorRelation(AggressorRelation.EX_SPOUSE)
                .caseDescription("Some description")
                .authority(Authority.POLICE)
                .actions(List.of(ActionTaken.POLICE, ActionTaken.OTHER))
                .status(ReportStatus.PENDING)
                .rejectionReason("")
                .build();
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("partner@example.com");
        User user = new User();
        user.setEmail("partner@example.com");

        when(userRepository.findByEmail("partner@example.com")).thenReturn(Optional.of(user));

        service.submitReport(request, userDetails);
        ArgumentCaptor<PendingGenderViolenceReport> captor = ArgumentCaptor.forClass(PendingGenderViolenceReport.class);
        verify(reportRepository, times(1)).save(captor.capture());

        PendingGenderViolenceReport saved = captor.getValue();
        assertEquals(Gender.FEMALE, saved.getGender());
        assertEquals(District.ALATAU, saved.getDistrict());
        assertEquals(30, saved.getAge());
    }
    @Test
    void getRejectedReports_shouldReturnListOfResponses() {
        UUID userId = UUID.randomUUID();

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                userId.toString(), "", List.of()
        );

        User user = new User();
        user.setId(userId);

        PendingGenderViolenceReport report = new PendingGenderViolenceReport();
        report.setId(UUID.randomUUID());
        report.setGender(Gender.FEMALE);
        report.setDate(LocalDate.now());
        report.setDistrict(District.ALATAU);
        report.setAge(25);
        report.setViolenceType(ViolenceType.PSYCHOLOGICAL);
        report.setLocation(LocationType.PUBLIC_PLACE);
        report.setTimeOfDay(TimeOfDay.NIGHT);
        report.setSocialStatus(SocialStatus.UNEMPLOYED);
        report.setAggressorRelation(AggressorRelation.FRIEND);
        report.setCaseDescription("test case");
        report.setAuthority(Authority.POLICE);
        report.setActions(List.of(ActionTaken.OTHER));
        report.setRejectionReason("Incorrect info");
        report.setSubmittedBy(user);
        report.setStatus(ReportStatus.REJECTED);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reportRepository.findBySubmittedByAndStatus(user, ReportStatus.REJECTED))
                .thenReturn(List.of(report));

        var result = service.getRejectedGenderViolenceReports(userDetails);

        assertEquals(1, result.size());
        assertEquals(Gender.FEMALE, result.get(0).getGender());
    }
    @Test
    void updateRejectedReport_shouldUpdateSuccessfully() {
        UUID reportId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                userId.toString(), "", List.of()
        );

        User user = new User();
        user.setId(userId);

        PendingGenderViolenceReport report = new PendingGenderViolenceReport();
        report.setId(reportId);
        report.setStatus(ReportStatus.REJECTED);
        report.setSubmittedBy(user);

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        PendingGenderViolenceReportRequest request = PendingGenderViolenceReportRequest.builder()
                .gender(Gender.MALE)
                .date(LocalDate.now())
                .district(District.TURKSIB)
                .age(28)
                .violenceType(ViolenceType.SEXUAL)
                .location(LocationType.PUBLIC_PLACE)
                .timeOfDay(TimeOfDay.MORNING)
                .socialStatus(SocialStatus.STUDENT)
                .aggressorRelation(AggressorRelation.STRANGER)
                .caseDescription("Updated case")
                .authority(Authority.NGO)
                .actions(List.of(ActionTaken.SHELTER))
                .build();

        service.updateRejectedGenderViolenceReport(reportId, request, userDetails);

        verify(reportRepository).save(report);
        assertEquals(Gender.MALE, report.getGender());
        assertEquals(ReportStatus.PENDING, report.getStatus());
        assertNull(report.getRejectionReason());
    }
    @Test
    void updateRejectedReport_shouldThrowIfNotFound() {
        UUID reportId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                userId.toString(), "", List.of()
        );

        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->
                service.updateRejectedGenderViolenceReport(reportId, new PendingGenderViolenceReportRequest(), userDetails));
    }

}
