package kz.kbtu.dto.report;

import kz.kbtu.enums.ReportStatus;
import kz.kbtu.enums.ReportYear;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
@Setter
@Builder
public class PendingMarriageDivorceReportResponse {
    private UUID id;
    private ReportYear reportYear;
    private String district;
    private int marriageCount;
    private int divorceCount;
    private BigDecimal ratioDivorcesToMarriagePercent;
    private BigDecimal averageAge;
    private String source;
    private UUID submittedBy;
    private ReportStatus status;
    private String rejectionReason;
}
