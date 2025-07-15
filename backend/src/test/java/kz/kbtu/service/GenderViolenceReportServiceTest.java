package kz.kbtu.service;

import jakarta.persistence.EntityNotFoundException;
import kz.kbtu.entity.User;
import kz.kbtu.entity.report.GenderViolenceReport;
import kz.kbtu.entity.report.PendingGenderViolenceReport;
import kz.kbtu.enums.*;
import kz.kbtu.repository.GenderViolenceReportRepository;
import kz.kbtu.repository.PendingGenderViolenceReportRepository;
import kz.kbtu.service.report.GenderViolenceReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GenderViolenceReportServiceTest {
    private PendingGenderViolenceReportRepository pendingRepository;
    private GenderViolenceReportRepository  reportRepository;
    private GenderViolenceReportService service;

    @BeforeEach
    public void setup() {
        pendingRepository = mock(PendingGenderViolenceReportRepository.class);
        reportRepository = mock(GenderViolenceReportRepository.class);
        service = new GenderViolenceReportService(pendingRepository, reportRepository);

    }

    public List<PendingGenderViolenceReport> getAllPendingReports() {
        return pendingRepository.findAll()
                .stream()
                .filter(report -> report.getStatus() == ReportStatus.PENDING)
                .toList();
    }

    @Test
    void approveReport_shouldReturn200(){
        UUID id = UUID.randomUUID();
        PendingGenderViolenceReport pending = new PendingGenderViolenceReport();
        pending.setId(id);
        pending.setDate(LocalDate.now());
        pending.setGender(Gender.FEMALE);
        pending.setDistrict(District.ALATAU);
        pending.setAge(30);
        pending.setViolenceType(ViolenceType.PHYSICAL);
        pending.setLocation(LocationType.HOME);
        pending.setTimeOfDay(TimeOfDay.MORNING);
        pending.setSocialStatus(SocialStatus.EMPLOYED);
        pending.setAggressorRelation(AggressorRelation.SPOUSE);
        pending.setCaseDescription("case");
        pending.setAuthority(Authority.POLICE);
        pending.setActions(List.of(ActionTaken.POLICE));

        when(pendingRepository.findById(id)).thenReturn(Optional.of(pending));

        service.approveReport(id);
        ArgumentCaptor<GenderViolenceReport> captor = ArgumentCaptor.forClass(GenderViolenceReport.class);
        verify(reportRepository).save(captor.capture());
        GenderViolenceReport savedReport = captor.getValue();

        assertEquals(ReportStatus.APPROVED, savedReport.getStatus());

        verify(reportRepository, times(1)).save(any(GenderViolenceReport.class));
        verify(pendingRepository, times(1)).delete(pending);
        verify(pendingRepository, never()).save(any());
    }

    @Test
    void approveReport_shouldThrowEntityNotFoundException(){
        UUID id = UUID.randomUUID();
        PendingGenderViolenceReport pending = new PendingGenderViolenceReport();
        pending.setId(id);
        when(pendingRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.approveReport(id));
    }
    @Test
    void rejectReport_shouldReturn200(){
        UUID id = UUID.randomUUID();
        PendingGenderViolenceReport pending = new PendingGenderViolenceReport();
        pending.setId(id);
        when(pendingRepository.findById(id)).thenReturn(Optional.of(pending));
        service.rejectReport(id, "Incomplete data");
        assertEquals(ReportStatus.REJECTED, pending.getStatus());
        assertEquals("Incomplete data", pending.getRejectionReason());
        verify(pendingRepository, times(1)).save(pending);
    }

    @Test
    void rejectReport_shouldThrowEntityNotFoundException(){
        UUID id = UUID.randomUUID();
        when(pendingRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.rejectReport(id, "reason"));
    }
}
