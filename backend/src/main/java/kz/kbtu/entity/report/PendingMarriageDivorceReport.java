package kz.kbtu.entity.report;

import jakarta.persistence.*;
import kz.kbtu.enums.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

import kz.kbtu.entity.User;
@Entity
@Table(name = "pending_marriage_and_divorce_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PendingMarriageDivorceReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_year", nullable = false)
    private ReportYear reportYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "district", nullable = false )
    private District district;

    @Column(name = "marriage_count", nullable = false)
    private int marriageCount;

    @Column(name = "divorce_count", nullable = false)
    private int divorceCount;

    @Column(name = "ratio_divorces_to_marriage_percent", nullable = false, precision = 5, scale = 2)
    private BigDecimal ratioDivorcesToMarriagePercent;

    @Column(name = "average_age", nullable = false, precision = 4, scale = 1)
    private BigDecimal averageAge;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    private Source source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitted_by", nullable = false )
    private User submittedBy;

    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.PENDING;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();


    public BigDecimal getDivorceRatioPercent() {
        if (marriageCount == 0) return BigDecimal.ZERO;
        return BigDecimal.valueOf(divorceCount * 100.0 / marriageCount).setScale(2, RoundingMode.HALF_UP);
    }

}
