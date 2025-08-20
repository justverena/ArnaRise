package kz.kbtu.dto.report;

import kz.kbtu.entity.User;
import kz.kbtu.enums.District;
import kz.kbtu.enums.ReportStatus;
import kz.kbtu.enums.ReportYear;
import kz.kbtu.enums.Source;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendingMarriageDivorceReportRequest {
    private ReportYear reportYear;
    private District district;
    private int marriageCount;
    private int divorceCount;
    private BigDecimal averageAge;
    private Source source;
    private User submittedBy;
    private ReportStatus status = ReportStatus.PENDING;
    private String rejectionReason;

}
