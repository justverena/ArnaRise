package kz.kbtu.repository;

import kz.kbtu.entity.report.PendingGenderViolenceReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PendingGenderViolenceReportRepository extends JpaRepository<PendingGenderViolenceReport, UUID> {
}
