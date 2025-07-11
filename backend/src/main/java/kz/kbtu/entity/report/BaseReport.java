package kz.kbtu.entity.report;

import jakarta.persistence.*;

import kz.kbtu.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import kz.kbtu.enums.ReportStatus;
import kz.kbtu.enums.Gender;
import kz.kbtu.enums.District;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "report_date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false )
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false )
    private District district;

    @Column(nullable = false )
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitted_by", nullable = false )
    private User submittedBy;

    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.PENDING;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Gender getGender() {
        return gender;
    }
    public District getDistrict() {
        return district;
    }
    public int getAge() {
        return age;
    }

    public User getSubmittedBy() {
        return submittedBy;
    }
    public ReportStatus getStatus() {
        return status;
    }
    public void setStatus(ReportStatus status) {
        this.status = status;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public void setDistrict(District district) {
        this.district = district;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setSubmittedBy(User submittedBy) {
        this.submittedBy = submittedBy;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
