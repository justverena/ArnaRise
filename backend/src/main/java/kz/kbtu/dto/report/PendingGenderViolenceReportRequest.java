package kz.kbtu.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import kz.kbtu.enums.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class PendingGenderViolenceReportRequest {

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

    private List<ActionTaken> actions;

//
//    public Gender getGender() {
//        return gender;
//    }
//    public LocalDate getDate() {
//        return date;
//    }
//
//    public District getDistrict() {
//        return district;
//    }
//
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public Authority getAuthority() {
//
//        return authority;
//    }
//    public TimeOfDay getTimeOfDay() {
//        return timeOfDay;
//
//    }
//    public SocialStatus getSocialStatus() {
//        return socialStatus;
//    }
//    public AggressorRelation getAggressorRelation() {
//
//        return aggressorRelation;
//    }
//    public String getCaseDescription() {
//        return caseDescription;
//    }
//
//    public LocationType getLocation() {
//        return location;
//    }
//    public List<ActionTaken> getActions() {
//        return actions;
//    }
//
//
//    public ViolenceType getViolenceType() {
//        return violenceType;
//    }
//    public ReportStatus getStatus() {
//        return status;
//    }
//    public String getRejectionReason() {
//        return rejectionReason;
//    }
}
