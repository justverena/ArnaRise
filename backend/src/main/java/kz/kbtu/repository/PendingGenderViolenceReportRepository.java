package kz.kbtu.repository;

import kz.kbtu.entity.User;
import kz.kbtu.entity.report.PendingGenderViolenceReport;
import kz.kbtu.enums.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PendingGenderViolenceReportRepository extends JpaRepository<PendingGenderViolenceReport, UUID> {
    List<PendingGenderViolenceReport> findBySubmittedByAndStatus(User submittedBy, ReportStatus status);
}
