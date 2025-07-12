package kz.kbtu.repository;

import kz.kbtu.entity.report.PendingMarriageDivorceReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PendingMarriageDivorceReportRepository extends JpaRepository<PendingMarriageDivorceReport, UUID> {

}
