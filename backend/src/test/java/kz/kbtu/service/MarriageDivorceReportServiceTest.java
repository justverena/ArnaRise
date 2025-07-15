package kz.kbtu.service;

import jakarta.persistence.EntityNotFoundException;
import kz.kbtu.entity.report.MarriageDivorceReport;
import kz.kbtu.entity.report.PendingMarriageDivorceReport;
import kz.kbtu.enums.*;
import kz.kbtu.repository.MarriageDivorceReportRepository;
import kz.kbtu.repository.PendingMarriageDivorceReportRepository;
import kz.kbtu.service.report.MarriageDivorceReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MarriageDivorceReportServiceTest {

        private PendingMarriageDivorceReportRepository pendingRepository;
        private MarriageDivorceReportRepository reportRepository;
        private MarriageDivorceReportService service;

        @BeforeEach
        public void setup() {
            pendingRepository = mock(PendingMarriageDivorceReportRepository.class);
            reportRepository = mock(MarriageDivorceReportRepository.class);
            service = new MarriageDivorceReportService(pendingRepository, reportRepository);

        }

        public List<PendingMarriageDivorceReport> getAllPendingReports() {
            return pendingRepository.findAll()
                    .stream()
                    .filter(report -> report.getStatus() == ReportStatus.PENDING)
                    .toList();
        }

        @Test
        void approveReport_shouldReturn200(){
            UUID id = UUID.randomUUID();
            PendingMarriageDivorceReport pending = new PendingMarriageDivorceReport();
            pending.setId(id);
            pending.setReportYear(ReportYear.Y2015);
            pending.setDistrict(District.ALATAU);
            pending.setMarriageCount(34);
            pending.setDivorceCount(40);
            pending.setAverageAge(BigDecimal.valueOf(29.5));
            pending.setSource(Source.CIVIL_REGISTRY);


            when(pendingRepository.findById(id)).thenReturn(Optional.of(pending));

            service.approveReport(id);
            ArgumentCaptor<MarriageDivorceReport> captor = ArgumentCaptor.forClass(MarriageDivorceReport.class);
            verify(reportRepository).save(captor.capture());
            MarriageDivorceReport savedReport = captor.getValue();

            assertEquals(ReportStatus.APPROVED, savedReport.getStatus());

            verify(reportRepository, times(1)).save(any(MarriageDivorceReport.class));
            verify(pendingRepository, times(1)).delete(pending);
            verify(pendingRepository, never()).save(any());
        }

        @Test
        void approveReport_shouldThrowEntityNotFoundException(){
            UUID id = UUID.randomUUID();
            PendingMarriageDivorceReport pending = new PendingMarriageDivorceReport();
            pending.setId(id);
            when(pendingRepository.findById(id)).thenReturn(Optional.empty());
            assertThrows(EntityNotFoundException.class, () -> service.approveReport(id));
        }
        @Test
        void rejectReport_shouldReturn200(){
            UUID id = UUID.randomUUID();
            PendingMarriageDivorceReport pending = new PendingMarriageDivorceReport();
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
