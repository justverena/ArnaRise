package kz.kbtu.repository;

import kz.kbtu.entity.User;
import kz.kbtu.entity.report.PendingMarriageDivorceReport;
import kz.kbtu.enums.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PendingMarriageDivorceReportRepository extends JpaRepository<PendingMarriageDivorceReport, UUID> {

    List<PendingMarriageDivorceReport> findBySubmittedByAndStatus(User user, ReportStatus reportStatus);
}
