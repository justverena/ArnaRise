package kz.kbtu.controller.indicator;

import kz.kbtu.dto.filter.FilterDto;
import kz.kbtu.dto.indicator.DivorceCountIndicator;
import kz.kbtu.dto.indicator.MarriageAvAgeIndicator;
import kz.kbtu.dto.indicator.MarriageCountIndicator;
import kz.kbtu.service.indicator.MarriageDivorceIndicatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public List<MarriageCountIndicator> getMarriageCountByYear(@ModelAttribute FilterDto filter) {
        return marriageDivorceIndicatorService.getMarriageCountByYear(filter);
    }

    @GetMapping("/divorce-count-by-year")
    public List<DivorceCountIndicator> getDivorceCountByYear(@ModelAttribute FilterDto filter) {
        return marriageDivorceIndicatorService.getDivorceCountByYear(filter);
    }

    @GetMapping("/marriage-av-age-by-year")
    public List<MarriageAvAgeIndicator> getMarriageAvAge(@ModelAttribute FilterDto filter) {
        return marriageDivorceIndicatorService.getMarriageAvAgeByYear(filter);
    }
}
