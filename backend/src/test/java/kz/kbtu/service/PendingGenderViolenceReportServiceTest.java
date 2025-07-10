package kz.kbtu.service;

import kz.kbtu.dto.report.PendingGenderViolenceReportRequest;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PendingGenderViolenceReportServiceTest {
    @Mock private PendingGenderViolenceReportRepository reportRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private PendingGenderViolenceReportService service;

    @Test
    void createReport_shouldSaveSuccessfully() {
//        PendingGenderViolenceReportRequest request = new PendingGenderViolenceReportRequest(Gender.FEMALE, LocalDate.now(), District.ALATAU, 30, ViolenceType.PHYSICAL, LocationType.HOME, TimeOfDay.EVENING, SocialStatus.EMPLOYED, AggressorRelation.EX_SPOUSE, "some description of the case", Authority.POLICE, List.of(ActionTaken.POLICE, ActionTaken.OTHER));
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


}
