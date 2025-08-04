package kz.kbtu.controller;

import kz.kbtu.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
public class EnumIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldReturnTranslatedGenderEnumRu() throws Exception {
        mockMvc.perform(get("/api/enums/gender")
                .header("Accept-Language", "ru")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(Gender.values().length)))
                .andExpect(jsonPath("$[?(@.key=='MALE')].value").value(contains("Мужской")))
                .andExpect(jsonPath("$[?(@.key=='FEMALE')].value").value(contains("Женский")));

    }

    @Test
    void shouldReturnTranslatedViolenceTypeEnumRu() throws Exception {
        mockMvc.perform(get("/api/enums/violence-type")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "ru")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(ViolenceType.values().length)))
                .andExpect(jsonPath("$[?(@.key=='PHYSICAL')].value").value(contains("Физическое")))
                .andExpect(jsonPath("$[?(@.key=='PSYCHOLOGICAL')].value").value(contains("Психологическое")));
    }

    @Test
    void shouldReturnTranslatedAggressorRelationEnumRu() throws Exception {
        mockMvc.perform(get("/api/enums/aggressor-relation")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "ru")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(AggressorRelation.values().length)))
                .andExpect(jsonPath("$[?(@.key=='SPOUSE')].value").value(contains("Супруг/а")));
    }

    @Test
    void shouldReturnTranslatedActionTakenEnumRu() throws Exception {
        mockMvc.perform(get("/api/enums/action-taken")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "ru")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(ActionTaken.values().length)))
                .andExpect(jsonPath("$[?(@.key=='POLICE')].value").value(contains("Полиция")));
    }

    @Test
    void shouldReturnTranslatedAuthorityEnumRu() throws Exception {
        mockMvc.perform(get("/api/enums/authority")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "ru")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(Authority.values().length)))
                .andExpect(jsonPath("$[?(@.key=='POLICE')].value").value(contains("Полиция")));
    }

    @Test
    void shouldReturnTranslatedDistrictEnumRu() throws Exception {
        mockMvc.perform(get("/api/enums/district")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "ru")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(District.values().length)))
                .andExpect(jsonPath("$[?(@.key=='ALATAU')].value").value(contains("Алатауский")));
    }

    @Test
    void shouldReturnTranslatedLocationTypeEnumRu() throws Exception {
        mockMvc.perform(get("/api/enums/location-type")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "ru")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(LocationType.values().length)))
                .andExpect(jsonPath("$[?(@.key=='HOME')].value").value(contains("Дом")));
    }

    @Test
    void shouldReturnTranslatedReportStatusEnumRu() throws Exception {
        mockMvc.perform(get("/api/enums/report-status")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "ru")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(ReportStatus.values().length)))
                .andExpect(jsonPath("$[?(@.key=='PENDING')].value").value(contains("Ожидает рассмотрения")));
    }

    @Test
    void shouldReturnTranslatedReportYearEnumRu() throws Exception {
        mockMvc.perform(get("/api/enums/report-year")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "ru")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(ReportYear.values().length)))
                .andExpect(jsonPath("$[?(@.key=='Y2025')].value").value(contains("2025")));
    }

    @Test
    void shouldReturnTranslatedSocialStatusEnumRu() throws Exception {
        mockMvc.perform(get("/api/enums/social-status")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "ru")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(SocialStatus.values().length)))
                .andExpect(jsonPath("$[?(@.key=='EMPLOYED')].value").value(contains("Трудоустроен(а)")));
    }

    @Test
    void shouldReturnTranslatedSourceEnumRu() throws Exception {
        mockMvc.perform(get("/api/enums/source")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "ru")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(Source.values().length)))
                .andExpect(jsonPath("$[?(@.key=='NATIONAL_STATISTICS')].value").value(contains("Национальная статистика")));
    }

    @Test
    void shouldReturnTranslatedTimeOfDayEnumRu() throws Exception {
        mockMvc.perform(get("/api/enums/time-of-day")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "ru")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(TimeOfDay.values().length)))
                .andExpect(jsonPath("$[?(@.key=='MORNING')].value").value(contains("Утро")));
    }



    @Test
    void shouldReturnTranslatedGenderEnumKz() throws Exception {
        mockMvc.perform(get("/api/enums/gender")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "kz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.key=='MALE')].value").value(contains("Ер")))
                .andExpect(jsonPath("$[?(@.key=='FEMALE')].value").value(contains("Әйел")));
    }

    @Test
    void shouldReturnTranslatedActionTakenEnumKz() throws Exception {
        mockMvc.perform(get("/api/enums/action-taken")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "kz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.key=='POLICE')].value").value(contains("Полиция")))
                .andExpect(jsonPath("$[?(@.key=='SHELTER')].value").value(contains("Баспана")));
    }

    @Test
    void shouldReturnTranslatedAggressorRelationEnumKz() throws Exception {
        mockMvc.perform(get("/api/enums/aggressor-relation")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "kz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.key=='SPOUSE')].value").value(contains("Жұбай")));
    }

    @Test
    void shouldReturnTranslatedLocationTypeEnumKz() throws Exception {
        mockMvc.perform(get("/api/enums/location-type")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "kz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.key=='HOME')].value").value(contains("Үй")));
    }

    @Test
    void shouldReturnTranslatedViolenceTypeEnumKz() throws Exception {
        mockMvc.perform(get("/api/enums/violence-type")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "kz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.key=='PHYSICAL')].value").value(contains("Физикалық")));
    }

    @Test
    void shouldReturnTranslatedTimeOfDayEnumKz() throws Exception {
        mockMvc.perform(get("/api/enums/time-of-day")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "kz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.key=='MORNING')].value").value(contains("Таң")));
    }

    @Test
    void shouldReturnTranslatedSocialStatusEnumKz() throws Exception {
        mockMvc.perform(get("/api/enums/social-status")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "kz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.key=='EMPLOYED')].value").value(contains("Жұмыс істейді")));
    }

    @Test
    void shouldReturnTranslatedReportStatusEnumKz() throws Exception {
        mockMvc.perform(get("/api/enums/report-status")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "kz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.key=='PENDING')].value").value(contains("Күтуде")));
    }

    @Test
    void shouldReturnTranslatedReportYearEnumKz() throws Exception {
        mockMvc.perform(get("/api/enums/report-year")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "kz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.key=='Y2025')].value").value(contains("2025")));
    }

    @Test
    void shouldReturnTranslatedAuthorityEnumKz() throws Exception {
        mockMvc.perform(get("/api/enums/authority")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "kz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.key=='POLICE')].value").value(contains("Полиция")));
    }

    @Test
    void shouldReturnTranslatedDistrictEnumKz() throws Exception {
        mockMvc.perform(get("/api/enums/district")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "kz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.key=='ALATAU')].value").value(contains("Алатау")));
    }

    @Test
    void shouldReturnTranslatedSourceEnumKz() throws Exception {
        mockMvc.perform(get("/api/enums/source")
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "kz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.key=='NATIONAL_STATISTICS')].value").value(contains("Ұлттық статистика")));
    }
}
