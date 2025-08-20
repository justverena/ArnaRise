package kz.kbtu.dto.report;

import kz.kbtu.enums.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public PendingGenderViolenceReportResponse(UUID id, LocalDate date, Gender gender, District district, int age, ViolenceType violenceType, LocationType location, TimeOfDay timeOfDay, SocialStatus socialStatus, AggressorRelation aggressorRelation, String caseDescription, List<ActionTaken> actions, Authority authority, List<ActionTaken> actions1, ReportStatus status, String rejectionReason) {
        this.id = id;
        this.date = date;
        this.gender = gender;
        this.district = district;
        this.age = age;
        this.violenceType = violenceType;
        this.location = location;
        this.timeOfDay = timeOfDay;
        this.socialStatus = socialStatus;
        this.aggressorRelation = aggressorRelation;
        this.caseDescription = caseDescription;
        this.actions = actions;
        this.authority = authority;
        this.rejectionReason = rejectionReason;
        this.status = status;
        this.actions = actions1;

    }
}
