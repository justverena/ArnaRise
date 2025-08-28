package kz.kbtu.service.indicator;

import kz.kbtu.dto.indicator.DivorceCountIndicator;
import kz.kbtu.dto.indicator.MarriageCountIndicator;
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
    public List<MarriageCountIndicator> getMarriageCountByYear() {
        List<Object[]> data = marriageDivorceReportRepository.getMarriageCountByYear();
        return data.stream().map(row -> {
            ReportYear year = (ReportYear) row[0];
            Long marriageCount = (Long) row[1];
            return new MarriageCountIndicator(year.getValue(), marriageCount);
        })
                .collect(Collectors.toList());
    }

    public List<DivorceCountIndicator> getDivorceCountByYear() {
        List<Object[]> data = marriageDivorceReportRepository.getDivorceCountByYear();
        return data.stream().map(row -> {
            ReportYear year = (ReportYear) row[0];
            Long divorceCount = (Long) row[1];
            return new DivorceCountIndicator(year.getValue(), divorceCount);
        })
                .collect(Collectors.toList());
    }
}
