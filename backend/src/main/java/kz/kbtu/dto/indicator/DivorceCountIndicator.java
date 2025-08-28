package kz.kbtu.dto.indicator;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DivorceCountIndicator {
    private int reportYear;
    private Long divorceCount;
}
