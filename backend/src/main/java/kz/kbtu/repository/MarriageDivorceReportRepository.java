package kz.kbtu.repository;

import kz.kbtu.dto.indicator.MarriageCountIndicator;
import kz.kbtu.entity.report.MarriageDivorceReport;
import kz.kbtu.enums.District;
import kz.kbtu.enums.Gender;
import kz.kbtu.enums.ReportYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface MarriageDivorceReportRepository extends JpaRepository<MarriageDivorceReport, UUID>, JpaSpecificationExecutor<MarriageDivorceReport> {
    @Query("select r.reportYear, sum(r.marriageCount) " + "from MarriageDivorceReport r " + "where(:district is null or r.district = :district)" + "group by r.reportYear " + "order by r.reportYear ")
    List<Object[]> getMarriageCountByYear(
            @Param("district")District district
            );

    @Query("select r.reportYear, sum(r.divorceCount) " + "from MarriageDivorceReport r " + "where(:district is null or r.district = :district)" + "group by r.reportYear " + "order by r.reportYear ")
    List<Object[]> getDivorceCountByYear(
            @Param("district")District district
    );

    @Query("select r.reportYear, AVG(r.averageAge) " + "from MarriageDivorceReport r " + "where(:district is null or r.district = :district)" + "group by r.reportYear " + "order by r.reportYear ")
    List<Object[]> getMarriageAvAgeByYear(
            @Param("district")District district
    );
}
