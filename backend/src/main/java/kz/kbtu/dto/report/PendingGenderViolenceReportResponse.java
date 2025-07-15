package kz.kbtu.dto.report;

import kz.kbtu.enums.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@Builder
public class PendingGenderViolenceReportResponse {
    private UUID id;
    private Gender gender;
    private LocalDate date;
    private District district;
    private Integer age;

    private ViolenceType violenceType;
    private LocationType location;
    private TimeOfDay timeOfDay;
    private SocialStatus socialStatus;
    private AggressorRelation aggressorRelation;
    private String caseDescription;
    private Authority authority;
    private String rejectionReason;

    private ReportStatus status;
    private UUID submittedBy;

    private List<ActionTaken> actions;
}
