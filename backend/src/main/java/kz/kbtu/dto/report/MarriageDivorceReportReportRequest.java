package kz.kbtu.dto.report;

import kz.kbtu.entity.User;
import kz.kbtu.enums.District;
import kz.kbtu.enums.ReportStatus;
import kz.kbtu.enums.ReportYear;
import kz.kbtu.enums.Source;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarriageDivorceReportReportRequest {
    private ReportYear reportYear;
    private District district;
    private int marriageCount;
    private int divorceCount;
    private BigDecimal ratioDivorcesToMarriagePercent;
    private BigDecimal averageAge;
    private Source source;
    private User submittedBy;
    private ReportStatus status = ReportStatus.PENDING;

}
