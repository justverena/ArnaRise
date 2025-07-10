package kz.kbtu.repository;

import kz.kbtu.entity.report.GenderViolenceReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GenderViolenceReportRepository extends JpaRepository<GenderViolenceReport, UUID> {

}
