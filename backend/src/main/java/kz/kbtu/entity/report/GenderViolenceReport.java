package kz.kbtu.entity.report;


import jakarta.persistence.*;
import kz.kbtu.dto.report.GenderViolenceReportRequest;
import kz.kbtu.entity.User;
import kz.kbtu.enums.*;
import lombok.*;

import java.util.List;

@Entity
@Table (name = "gender_violence_reports")
@NoArgsConstructor
@AllArgsConstructor
public class GenderViolenceReport extends BaseReport {

    @Enumerated(EnumType.STRING)
    @Column(name = "violence_type", nullable = false)
    private ViolenceType violenceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "location", nullable = false)
    private LocationType location;

    @Enumerated(EnumType.STRING)
    @Column(name = "time_of_day", nullable = false)
    private TimeOfDay timeOfDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_status", nullable = false)
    private SocialStatus socialStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "aggressor_relation", nullable = false)
    private AggressorRelation aggressorRelation;

    @Column(name = "case_description", columnDefinition = "TEXT")
    private String caseDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "measures_taken_by")
    private Authority authority;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "gender_violence_report_actions", joinColumns = @JoinColumn(name = "report_id"))
    @Column(name = "action")
    private List<ActionTaken> actions;

    public void setActions(List<ActionTaken> actions){
        this.actions = actions;
    }
    public void setCaseDescription(String caseDescription){
        this.caseDescription = caseDescription;
    }
    public void setSocialStatus(SocialStatus socialStatus){
        this.socialStatus = socialStatus;
    }
    public void setAggressorRelation(AggressorRelation aggressorRelation){
        this.aggressorRelation = aggressorRelation;
    }
    public void setTimeOfDay(TimeOfDay timeOfDay){
        this.timeOfDay = timeOfDay;
    }
    public void setLocation(LocationType location){
        this.location = location;
    }
    public void setViolenceType(ViolenceType violenceType){
        this.violenceType = violenceType;
    }
    public void setAuthority(Authority authority){
        this.authority = authority;
    }

    public Object getCaseDescription() {
        return caseDescription;
    }

//    public static GenderViolenceReport createFromRequest(GenderViolenceReportRequest req, User user) {
//        GenderViolenceReport report = new GenderViolenceReport();
//        report.setDate(req.getDate());
//        report.setGender(req.getGender());
//        report.setDistrict(req.getDistrict());
//        report.setAge(req.getAge());
//
//        report.setViolenceType(req.getViolenceType());
//        report.setLocation(req.getLocation());
//        report.setTimeOfDay(req.getTimeOfDay());
//        report.setSocialStatus(req.getSocialStatus());
//        report.setAggressorRelation(req.getAggressorRelation());
//        report.setCaseDescription(req.getCaseDescription());
//        report.setAuthority(req.getAuthority());
//        report.setActions(req.getActions());
//
//        report.setSubmittedBy(user);
//
//        return report;
//    }
}
