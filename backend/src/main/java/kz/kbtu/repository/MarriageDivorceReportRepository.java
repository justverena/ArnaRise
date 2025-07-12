package kz.kbtu.repository;

import kz.kbtu.entity.report.MarriageDivorceReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MarriageDivorceReportRepository extends JpaRepository<MarriageDivorceReport, UUID> {

}
