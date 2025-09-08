package kz.kbtu.service;

import kz.kbtu.dto.filter.FilterDto;
import kz.kbtu.dto.indicator.DivorceCountIndicator;
import kz.kbtu.dto.indicator.MarriageAvAgeIndicator;
import kz.kbtu.dto.indicator.MarriageCountIndicator;
import kz.kbtu.dto.indicator.RatioIndicator;
import kz.kbtu.enums.District;
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
import static org.mockito.Mockito.*;

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
        FilterDto filter = new FilterDto(District.AUEZOV);
        List<Object[]> mockData = Arrays.asList(
                new Object[]{ReportYear.Y2020, 1200L},
                new Object[]{ReportYear.Y2021, 1500L}
        );
        when(repository.getMarriageCountByYear(filter.getDistrict())).thenReturn(mockData);

        List<MarriageCountIndicator> result = service.getMarriageCountByYear(filter);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getReportYear()).isEqualTo(2020);
        assertThat(result.get(0).getMarriageCount()).isEqualTo(1200L);
        assertThat(result.get(1).getReportYear()).isEqualTo(2021);
        assertThat(result.get(1).getMarriageCount()).isEqualTo(1500L);

        verify(repository, times(1))
                .getMarriageCountByYear(filter.getDistrict());
    }

    @Test
    void testGetDivorceCountByYear(){
        FilterDto filter = new FilterDto(District.AUEZOV);
        List<Object[]> mockData = Arrays.asList(
                new Object[]{ReportYear.Y2015, 1300L},
                new Object[]{ReportYear.Y2016, 2000L}
        );

        when(repository.getDivorceCountByYear(filter.getDistrict())).thenReturn(mockData);

        List<DivorceCountIndicator> result = service.getDivorceCountByYear(filter);
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getReportYear()).isEqualTo(2015);
        assertThat(result.get(0).getDivorceCount()).isEqualTo(1300L);
        assertThat(result.get(1).getReportYear()).isEqualTo(2016);
        assertThat(result.get(1).getDivorceCount()).isEqualTo(2000L);

        verify(repository, times(1))
                .getDivorceCountByYear(filter.getDistrict());
    }


    @Test
    void testGetMarriageAvAgeByYear(){
        FilterDto filter = new FilterDto(District.AUEZOV);
        List<Object[]> mockData = Arrays.asList(
                new Object[]{ReportYear.Y2017, 26.7},
                new Object[]{ReportYear.Y2019, 35.5}
        );
        when(repository.getMarriageAvAgeByYear(filter.getDistrict())).thenReturn(mockData);

        List<MarriageAvAgeIndicator> result = service.getMarriageAvAgeByYear(filter);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getReportYear()).isEqualTo(2017);
        assertThat(result.get(0).getMarriageAvAge()).isEqualTo(26.7);
        assertThat(result.get(1).getReportYear()).isEqualTo(2019);
        assertThat(result.get(1).getMarriageAvAge()).isEqualTo(35.5);

        verify(repository, times(1))
                .getMarriageAvAgeByYear(filter.getDistrict());
    }

    @Test
    void testGetRatioYear(){
        FilterDto filter = new FilterDto(District.AUEZOV);
        List<Object[]> mockData = Arrays.asList(
                new Object[]{ReportYear.Y2017, 333.8},
                new Object[]{ReportYear.Y2019, 560.9}
        );
        when(repository.getRatioByYear(filter.getDistrict())).thenReturn(mockData);

        List<RatioIndicator> result = service.getRatioByYear(filter);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getReportYear()).isEqualTo(2017);
        assertThat(result.get(0).getRatioDivorcesToMarriagePercent()).isEqualTo(333.8);
        assertThat(result.get(1).getReportYear()).isEqualTo(2019);
        assertThat(result.get(1).getRatioDivorcesToMarriagePercent()).isEqualTo(560.9);

        verify(repository, times(1))
                .getRatioByYear(filter.getDistrict());
    }
}
