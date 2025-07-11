package kz.kbtu.dto.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RejectionRequest {
    private String rejectionReason;
    protected void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
    public String getRejectionReason() {
        return rejectionReason;
    }
}