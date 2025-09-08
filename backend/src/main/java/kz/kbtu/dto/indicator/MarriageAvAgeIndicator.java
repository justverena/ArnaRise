package kz.kbtu.dto.indicator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarriageAvAgeIndicator {
    private int reportYear;
    private Double marriageAvAge;
}
