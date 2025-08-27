package kz.kbtu.dto.indicator;

import kz.kbtu.enums.ReportYear;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarriageCountIndicator {
    private int reportYear;
    private Long marriageCount;

}
