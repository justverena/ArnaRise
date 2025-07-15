package kz.kbtu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.kbtu.dto.auth.LoginRequest;
import kz.kbtu.dto.report.PendingGenderViolenceReportRequest;
import kz.kbtu.dto.report.RejectionRequest;
import kz.kbtu.entity.report.GenderViolenceReport;
import kz.kbtu.entity.report.PendingGenderViolenceReport;
import kz.kbtu.enums.*;
import kz.kbtu.repository.GenderViolenceReportRepository;
import kz.kbtu.repository.PendingGenderViolenceReportRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GenderViolenceReportIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private PendingGenderViolenceReportRepository pendingRepository;
    @Autowired private GenderViolenceReportRepository genderRepository;

    private String analystToken;
    private String partnerToken;
    private UUID pendingReportId;
    @BeforeAll
    void login() throws Exception {
        LoginRequest analystRequest = new LoginRequest("analyst@example.com", "analyst123");
        String analystResponse = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(analystRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        analystToken = objectMapper.readTree(analystResponse).get("token").asText();

        LoginRequest partnerRequest = new LoginRequest("partner@example.com", "partner123");
        String partnerResponse = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partnerRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        partnerToken = objectMapper.readTree(partnerResponse).get("token").asText();
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
                        .header("Authorization", "Bearer " + partnerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Report submitted successfully"));

        pendingReportId = pendingRepository.findAll()
                .stream()
                .filter(r ->r.getCaseDescription().equals("Some description"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pending report not found"))
                .getId();
    }

    @Test
    @Order(2)
    void analystCanSeePendingGenderViolenceReports() throws Exception {
        mockMvc.perform(get("/api/analyst/reports/gender-violence")
                        .header("Authorization", "Bearer " + analystToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(1)));
    }
    @Test
    @Order(3)
    void approveReport_shouldReturn200() throws Exception{
        mockMvc.perform(post("/api/analyst/reports/gender-violence/" + pendingReportId + "/approve")
                .header("Authorization", "Bearer " + analystToken))
                .andExpect(status().isOk());

        GenderViolenceReport saved = genderRepository.findAll().stream().filter(r -> r.getCaseDescription().equals("Some description"))
                .findFirst().orElse(null);
        assertNotNull(saved);
        assertEquals(ReportStatus.APPROVED, saved.getStatus());
        assertFalse(pendingRepository.findById(pendingReportId).isPresent());
    }
    @Test
    @Order(4)
    void rejectReport_shouldReturn200() throws Exception {
        PendingGenderViolenceReportRequest request = PendingGenderViolenceReportRequest.builder()
                .gender(Gender.FEMALE)
                .date(LocalDate.now())
                .district(District.MEDEU)
                .age(40)
                .violenceType(ViolenceType.SEXUAL)
                .location(LocationType.PUBLIC_PLACE)
                .timeOfDay(TimeOfDay.NIGHT)
                .socialStatus(SocialStatus.UNEMPLOYED)
                .aggressorRelation(AggressorRelation.OTHER)
                .caseDescription("Rejected case")
                .authority(Authority.POLICE)
                .actions(List.of(ActionTaken.OTHER))
                .status(ReportStatus.PENDING)
                .rejectionReason("")
                .build();

        mockMvc.perform(post("/api/partner/reports/gender-violence")
                        .header("Authorization", "Bearer " + partnerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        UUID toRejectId = pendingRepository.findAll().stream()
                .filter(r -> r.getCaseDescription().equals("Rejected case"))
                .findFirst()
                .orElseThrow().getId();

        String rejectionJson = objectMapper.writeValueAsString(
                new RejectionRequest() {{
                    setRejectionReason("Rejection reason incomplete data");
                }}
        );

        mockMvc.perform(post("/api/analyst/reports/gender-violence/" + toRejectId + "/reject")
                        .header("Authorization", "Bearer " + analystToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rejectionJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Report rejected and returned to partner with reason."));

        var rejected = pendingRepository.findById(toRejectId).orElse(null);
        assertNotNull(rejected);
        assertEquals(ReportStatus.REJECTED, rejected.getStatus());
        assertEquals("Rejection reason incomplete data", rejected.getRejectionReason());
    }

    @Test
    @Order(5)
    void submitReport_shouldReturn403_whenUnauthorized() throws Exception {
        PendingGenderViolenceReportRequest request = PendingGenderViolenceReportRequest.builder()
                .gender(Gender.FEMALE)
                .date(LocalDate.now())
                .district(District.ALMALY)
                .age(25)
                .violenceType(ViolenceType.ECONOMIC)
                .location(LocationType.HOME)
                .timeOfDay(TimeOfDay.MORNING)
                .socialStatus(SocialStatus.STUDENT)
                .aggressorRelation(AggressorRelation.SPOUSE)
                .caseDescription("Unauthorized partner attempt")
                .authority(Authority.SOCIAL_SERVICES)
                .actions(List.of(ActionTaken.OTHER))
                .status(ReportStatus.PENDING)
                .rejectionReason("")
                .build();

        mockMvc.perform(post("/api/partner/reports/gender-violence")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(6)
    void rejectReport_shouldReturn403_whenUnauthorized() throws Exception {
        PendingGenderViolenceReportRequest request = PendingGenderViolenceReportRequest.builder()
                .gender(Gender.MALE)
                .date(LocalDate.now())
                .district(District.BOSTANDYK)
                .age(35)
                .violenceType(ViolenceType.PSYCHOLOGICAL)
                .location(LocationType.PUBLIC_PLACE)
                .timeOfDay(TimeOfDay.NIGHT)
                .socialStatus(SocialStatus.EMPLOYED)
                .aggressorRelation(AggressorRelation.SPOUSE)
                .caseDescription("Unauthorized analyst attempt")
                .authority(Authority.NGO)
                .actions(List.of(ActionTaken.OTHER))
                .status(ReportStatus.PENDING)
                .rejectionReason("")
                .build();

        mockMvc.perform(post("/api/partner/reports/gender-violence")
                        .header("Authorization", "Bearer " + partnerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        UUID reportId = pendingRepository.findAll().stream()
                .filter(r -> r.getCaseDescription().equals("Unauthorized analyst attempt"))
                .findFirst()
                .orElseThrow()
                .getId();

        String rejectionJson = objectMapper.writeValueAsString(
                new RejectionRequest() {{
                    setRejectionReason("Missing token");
                }}
        );

        mockMvc.perform(post("/api/analyst/reports/gender-violence/" + reportId + "/reject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rejectionJson))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(7)
    void approveReport_shouldReturn403_whenPartnerTriesToApprove() throws Exception {
        PendingGenderViolenceReportRequest request = PendingGenderViolenceReportRequest.builder()
                .gender(Gender.FEMALE)
                .date(LocalDate.now())
                .district(District.TURKSIB)
                .age(28)
                .violenceType(ViolenceType.SEXUAL)
                .location(LocationType.WORKPLACE)
                .timeOfDay(TimeOfDay.AFTERNOON)
                .socialStatus(SocialStatus.UNEMPLOYED)
                .aggressorRelation(AggressorRelation.EX_SPOUSE)
                .caseDescription("Partner tries to approve")
                .authority(Authority.POLICE)
                .actions(List.of(ActionTaken.OTHER))
                .status(ReportStatus.PENDING)
                .rejectionReason("")
                .build();

        mockMvc.perform(post("/api/partner/reports/gender-violence")
                        .header("Authorization", "Bearer " + partnerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        UUID reportId = pendingRepository.findAll().stream()
                .filter(r -> r.getCaseDescription().equals("Partner tries to approve"))
                .findFirst()
                .orElseThrow()
                .getId();

        mockMvc.perform(post("/api/analyst/reports/gender-violence/" + reportId + "/approve")
                        .header("Authorization", "Bearer " + partnerToken))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(8)
    void submitReport_shouldReturn403_whenAnalystTriesToSubmit() throws Exception {
        PendingGenderViolenceReportRequest request = PendingGenderViolenceReportRequest.builder()
                .gender(Gender.MALE)
                .date(LocalDate.now())
                .district(District.NAURYZBAY)
                .age(45)
                .violenceType(ViolenceType.PHYSICAL)
                .location(LocationType.HOME)
                .timeOfDay(TimeOfDay.MORNING)
                .socialStatus(SocialStatus.EMPLOYED)
                .aggressorRelation(AggressorRelation.SPOUSE)
                .caseDescription("Analyst tries to submit")
                .authority(Authority.POLICE)
                .actions(List.of(ActionTaken.POLICE))
                .status(ReportStatus.PENDING)
                .rejectionReason("")
                .build();

        mockMvc.perform(post("/api/partner/reports/gender-violence")
                        .header("Authorization", "Bearer " + analystToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

}