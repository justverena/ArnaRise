package kz.kbtu.repository;

import kz.kbtu.dto.indicator.MarriageCountIndicator;
import kz.kbtu.entity.report.MarriageDivorceReport;
import kz.kbtu.enums.ReportYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface MarriageDivorceReportRepository extends JpaRepository<MarriageDivorceReport, UUID> {
    @Query("select r.reportYear, sum(r.marriageCount) " + "from MarriageDivorceReport r " + "group by r.reportYear " + "order by r.reportYear ")
    List<Object[]> getMarriageCountByYear();

}
