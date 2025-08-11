package kz.kbtu.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ReportShortResponse {
    private UUID id;
    private UUID submittedBy;
}
