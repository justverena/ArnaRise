package kz.kbtu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.kbtu.dto.auth.LoginRequest;
import kz.kbtu.dto.report.PendingGenderViolenceReportRequest;
import kz.kbtu.enums.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PartnerReportIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    private static String token;
    @BeforeAll
    void partnerLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest("partner@example.com", "partner123");
        String loginResponse = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        token = objectMapper.readTree(loginResponse).get("token").asText();
    }
    @Test
    @Order(1)
    void submitReport_shouldReturn200() throws Exception{
        PendingGenderViolenceReportRequest request = PendingGenderViolenceReportRequest.builder()
                .gender(Gender.FEMALE)
                .date(LocalDate.now())
                .district(District.ALATAU)
                .age(30)
                .violenceType(ViolenceType.PHYSICAL)
                .location(LocationType.HOME)
                .timeOfDay(TimeOfDay.EVENING)
                .socialStatus(SocialStatus.EMPLOYED)
                .aggressorRelation(AggressorRelation.EX_SPOUSE)
                .caseDescription("Some description")
                .authority(Authority.POLICE)
                .actions(List.of(ActionTaken.POLICE, ActionTaken.OTHER))
                .status(ReportStatus.PENDING)
                .rejectionReason("")
                .build();
        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/partner/reports/gender-violence")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Report submitted successfully"));
    }
}