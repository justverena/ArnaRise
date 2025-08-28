package kz.kbtu.service;

import kz.kbtu.dto.indicator.DivorceCountIndicator;
import kz.kbtu.dto.indicator.MarriageCountIndicator;
import kz.kbtu.enums.ReportYear;
import kz.kbtu.repository.MarriageDivorceReportRepository;
import kz.kbtu.service.indicator.MarriageDivorceIndicatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class MarriageDivorceIndicatorServiceTest {

    private MarriageDivorceIndicatorService service;
    @Mock
    private MarriageDivorceReportRepository repository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new MarriageDivorceIndicatorService(repository);
    }

    @Test
    void testGetMarriageCountByYear(){
        List<Object[]> mockData = Arrays.asList(
                new Object[]{ReportYear.Y2020, 1200L},
                new Object[]{ReportYear.Y2021, 1500L}
        );

        when(repository.getMarriageCountByYear()).thenReturn(mockData);

        List<MarriageCountIndicator> result = service.getMarriageCountByYear();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getReportYear()).isEqualTo(2020);
        assertThat(result.get(0).getMarriageCount()).isEqualTo(1200L);
        assertThat(result.get(1).getReportYear()).isEqualTo(2021);
        assertThat(result.get(1).getMarriageCount()).isEqualTo(1500L);
    }

    @Test
    void testGetDivorceCountByYear(){
        List<Object[]> mockData = Arrays.asList(
                new Object[]{ReportYear.Y2015, 1300L},
                new Object[]{ReportYear.Y2016, 2000L}
        );

        when(repository.getDivorceCountByYear()).thenReturn(mockData);

        List<DivorceCountIndicator> result = service.getDivorceCountByYear();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getReportYear()).isEqualTo(2015);
        assertThat(result.get(0).getDivorceCount()).isEqualTo(1300L);
        assertThat(result.get(1).getReportYear()).isEqualTo(2016);
        assertThat(result.get(1).getDivorceCount()).isEqualTo(2000L);
    }

}
