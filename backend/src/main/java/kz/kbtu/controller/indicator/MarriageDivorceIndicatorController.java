package kz.kbtu.controller.indicator;

import kz.kbtu.dto.indicator.DivorceCountIndicator;
import kz.kbtu.dto.indicator.MarriageCountIndicator;
import kz.kbtu.service.indicator.MarriageDivorceIndicatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/analyst/indicators/")
@PreAuthorize("hasAuthority('analyst')")
public class MarriageDivorceIndicatorController {
    private final MarriageDivorceIndicatorService marriageDivorceIndicatorService;

    @GetMapping("/marriage-count-by-year")
    public List<MarriageCountIndicator> getMarriageCountByYear() {
        return marriageDivorceIndicatorService.getMarriageCountByYear();
    }

    @GetMapping("/divorce-count-by-year")
    public List<DivorceCountIndicator> getDivorceCountByYear() {
        return marriageDivorceIndicatorService.getDivorceCountByYear();
    }
}
