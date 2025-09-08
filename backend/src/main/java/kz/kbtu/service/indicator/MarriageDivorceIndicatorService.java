package kz.kbtu.service.indicator;

import kz.kbtu.dto.filter.FilterDto;
import kz.kbtu.dto.indicator.DivorceCountIndicator;
import kz.kbtu.dto.indicator.MarriageAvAgeIndicator;
import kz.kbtu.dto.indicator.MarriageCountIndicator;
import kz.kbtu.dto.indicator.RatioIndicator;
import kz.kbtu.enums.ReportYear;
import kz.kbtu.repository.MarriageDivorceReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarriageDivorceIndicatorService {

    private MarriageDivorceReportRepository marriageDivorceReportRepository;
    public MarriageDivorceIndicatorService(MarriageDivorceReportRepository repository) {
        this.marriageDivorceReportRepository = repository;
    }
    public List<MarriageCountIndicator> getMarriageCountByYear(FilterDto filter) {
        List<Object[]> data = marriageDivorceReportRepository.getMarriageCountByYear(
                filter.getDistrict()
        );
        return data.stream().map(row -> {
            ReportYear year = (ReportYear) row[0];
            Long marriageCount = (Long) row[1];
            return new MarriageCountIndicator(year.getValue(), marriageCount);
        })
                .collect(Collectors.toList());
    }

    public List<DivorceCountIndicator> getDivorceCountByYear(FilterDto filter) {
        List<Object[]> data = marriageDivorceReportRepository.getDivorceCountByYear(
                filter.getDistrict()
        );
        return data.stream().map(row -> {
            ReportYear year = (ReportYear) row[0];
            Long divorceCount = (Long) row[1];
            return new DivorceCountIndicator(year.getValue(), divorceCount);
        })
                .collect(Collectors.toList());
    }

    public List<MarriageAvAgeIndicator> getMarriageAvAgeByYear(FilterDto filter) {
        List <Object[]> data = marriageDivorceReportRepository.getMarriageAvAgeByYear(
                filter.getDistrict()
        );
        return data.stream().map(row -> {
            ReportYear year = (ReportYear) row[0];
            Double marriageAvAge = (Double) row[1];
            return new MarriageAvAgeIndicator(year.getValue(), marriageAvAge);
        })
                .collect(Collectors.toList());
    }

    public List<RatioIndicator> getRatioByYear(FilterDto filter) {
        List <Object[]> data = marriageDivorceReportRepository.getRatioByYear(
                filter.getDistrict()
        );
        return data.stream().map(row -> {
                    ReportYear year = (ReportYear) row[0];
                    Double ratioDivorcesToMarriagePercent = (Double) row[1];
                    return new RatioIndicator(year.getValue(), ratioDivorcesToMarriagePercent);
                })
                .collect(Collectors.toList());
    }
}
