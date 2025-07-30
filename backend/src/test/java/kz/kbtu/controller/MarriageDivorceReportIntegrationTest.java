package kz.kbtu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.kbtu.dto.auth.LoginRequest;
import kz.kbtu.dto.report.PendingMarriageDivorceReportRequest;
import kz.kbtu.dto.report.RejectionRequest;
import kz.kbtu.entity.report.MarriageDivorceReport;
import kz.kbtu.enums.*;
import kz.kbtu.repository.MarriageDivorceReportRepository;
import kz.kbtu.repository.PendingMarriageDivorceReportRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MarriageDivorceReportIntegrationTest {
        @Autowired
        private MockMvc mockMvc;
        @Autowired private ObjectMapper objectMapper;
        @Autowired private PendingMarriageDivorceReportRepository pendingRepository;
        @Autowired private MarriageDivorceReportRepository reportRepository;

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
            PendingMarriageDivorceReportRequest request = PendingMarriageDivorceReportRequest.builder()
                    .reportYear(ReportYear.Y2015)
                    .district(District.ALATAU)
                    .marriageCount(34)
                    .divorceCount(40)
                    .averageAge(BigDecimal.valueOf(29.5))
                    .source(Source.CIVIL_REGISTRY)
                    .status(ReportStatus.PENDING)
                    .rejectionReason("")
                    .build();

            String json = objectMapper.writeValueAsString(request);
            mockMvc.perform(post("/api/partner/reports/marriage-divorce")
                            .header("Authorization", "Bearer " + partnerToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Report submitted successfully"));

            pendingReportId = pendingRepository.findAll()
                    .stream()
                    .filter(r ->r.getSource().equals(Source.CIVIL_REGISTRY))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Pending report not found"))
                    .getId();
        }
        @Test
        @Order(2)
        void analystCanSeePendingMarriageDivorceReports() throws Exception {
            mockMvc.perform(get("/api/analyst/reports/marriage-divorce")
                            .header("Authorization", "Bearer " + analystToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(1)));
        }

        @Test
        @Order(3)
        void approveReport_shouldReturn200() throws Exception{
            mockMvc.perform(post("/api/analyst/reports/marriage-divorce/" + pendingReportId + "/approve")
                            .header("Authorization", "Bearer " + analystToken))
                    .andExpect(status().isOk());

            MarriageDivorceReport saved = reportRepository.findAll().stream().filter(r -> r.getSource().equals(Source.CIVIL_REGISTRY))
                    .findFirst().orElse(null);
            assertNotNull(saved);
            assertEquals(ReportStatus.APPROVED, saved.getStatus());
            assertFalse(pendingRepository.findById(pendingReportId).isPresent());
        }
        @Test
        @Order(4)
        void rejectReport_shouldReturn200() throws Exception {
            PendingMarriageDivorceReportRequest request = PendingMarriageDivorceReportRequest.builder()
                    .reportYear(ReportYear.Y2015)
                    .district(District.ALATAU)
                    .marriageCount(34)
                    .divorceCount(40)
                    .averageAge(BigDecimal.valueOf(29.5))
                    .source(Source.CIVIL_REGISTRY)
                    .status(ReportStatus.PENDING)
                    .rejectionReason("")
                    .build();

            mockMvc.perform(post("/api/partner/reports/marriage-divorce")
                            .header("Authorization", "Bearer " + partnerToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());

            UUID toRejectId = pendingRepository.findAll().stream()
                    .filter(r -> r.getSource().equals(Source.CIVIL_REGISTRY))
                    .findFirst()
                    .orElseThrow().getId();

            String rejectionJson = objectMapper.writeValueAsString(
                    new RejectionRequest() {{
                        setRejectionReason("Rejection reason incomplete data");
                    }}
            );

            mockMvc.perform(post("/api/analyst/reports/marriage-divorce/" + toRejectId + "/reject")
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
            PendingMarriageDivorceReportRequest request = PendingMarriageDivorceReportRequest.builder()
                    .reportYear(ReportYear.Y2015)
                    .district(District.ALATAU)
                    .marriageCount(34)
                    .divorceCount(40)
                    .averageAge(BigDecimal.valueOf(29.5))
                    .source(Source.CIVIL_REGISTRY)
                    .status(ReportStatus.PENDING)
                    .rejectionReason("")
                    .build();

            mockMvc.perform(post("/api/partner/reports/marriage-divorce")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isForbidden());
        }

        @Test
        @Order(6)
        void rejectReport_shouldReturn403_whenUnauthorized() throws Exception {
            PendingMarriageDivorceReportRequest request = PendingMarriageDivorceReportRequest.builder()
                    .reportYear(ReportYear.Y2015)
                    .district(District.ALATAU)
                    .marriageCount(34)
                    .divorceCount(40)
                    .averageAge(BigDecimal.valueOf(29.5))
                    .source(Source.CIVIL_REGISTRY)
                    .status(ReportStatus.PENDING)
                    .rejectionReason("")
                    .build();

            mockMvc.perform(post("/api/partner/reports/marriage-divorce")
                            .header("Authorization", "Bearer " + partnerToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());

            UUID reportId = pendingRepository.findAll().stream()
                    .filter(r -> r.getSource().equals(Source.CIVIL_REGISTRY))
                    .findFirst()
                    .orElseThrow()
                    .getId();

            String rejectionJson = objectMapper.writeValueAsString(
                    new RejectionRequest() {{
                        setRejectionReason("Missing token");
                    }}
            );

            mockMvc.perform(post("/api/analyst/reports/marriage-divorce/" + reportId + "/reject")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(rejectionJson))
                    .andExpect(status().isForbidden());
        }

        @Test
        @Order(7)
        void approveReport_shouldReturn403_whenPartnerTriesToApprove() throws Exception {
            PendingMarriageDivorceReportRequest request = PendingMarriageDivorceReportRequest.builder()
                    .reportYear(ReportYear.Y2015)
                    .district(District.ALATAU)
                    .marriageCount(34)
                    .divorceCount(40)
                    .averageAge(BigDecimal.valueOf(29.5))
                    .source(Source.CIVIL_REGISTRY)
                    .status(ReportStatus.PENDING)
                    .rejectionReason("")
                    .build();
            mockMvc.perform(post("/api/partner/reports/marriage-divorce")
                            .header("Authorization", "Bearer " + partnerToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());

            UUID reportId = pendingRepository.findAll().stream()
                    .filter(r -> r.getSource().equals(Source.CIVIL_REGISTRY))
                    .findFirst()
                    .orElseThrow()
                    .getId();

            mockMvc.perform(post("/api/analyst/reports/marriage-divorce/" + reportId + "/approve")
                            .header("Authorization", "Bearer " + partnerToken))
                    .andExpect(status().isForbidden());
        }

        @Test
        @Order(8)
        void submitReport_shouldReturn403_whenAnalystTriesToSubmit() throws Exception {
            PendingMarriageDivorceReportRequest request = PendingMarriageDivorceReportRequest.builder()
                    .reportYear(ReportYear.Y2015)
                    .district(District.ALATAU)
                    .marriageCount(34)
                    .divorceCount(40)
                    .averageAge(BigDecimal.valueOf(29.5))
                    .source(Source.CIVIL_REGISTRY)
                    .status(ReportStatus.PENDING)
                    .rejectionReason("")
                    .build();

            mockMvc.perform(post("/api/partner/reports/marriage-divorce")
                            .header("Authorization", "Bearer " + analystToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isForbidden());
        }

        @Test
        @Order(9)
        void partnerCanGetRejectedReports() throws Exception {
            PendingMarriageDivorceReportRequest request = PendingMarriageDivorceReportRequest.builder()
                    .reportYear(ReportYear.Y2018)
                    .district(District.MEDEU)
                    .marriageCount(20)
                    .divorceCount(15)
                    .averageAge(BigDecimal.valueOf(30.0))
                    .source(Source.NATIONAL_STATISTICS)
                    .status(ReportStatus.PENDING)
                    .rejectionReason("")
                    .build();

            mockMvc.perform(post("/api/partner/reports/marriage-divorce")
                            .header("Authorization", "Bearer " + partnerToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());

            UUID reportId = pendingRepository.findAll().stream().filter(r -> r.getSource() == Source.NATIONAL_STATISTICS)
                    .findFirst()
                    .orElseThrow()
                    .getId();
            RejectionRequest rejection = new RejectionRequest();
            rejection.setRejectionReason("Missing required data");

            mockMvc.perform(post("/api/analyst/reports/marriage-divorce/" + reportId + "/reject")
            .header("Authorization", "Bearer " + analystToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(rejection)))
                    .andExpect(status().isOk());

            mockMvc.perform((get("/api/partner/reports/marriage-divorce/rejected")
                    .header("Authorization", "Bearer " + partnerToken)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].status").value("REJECTED"))
                    .andExpect(jsonPath("$[0].rejectionReason").value("Missing required data"));
        }

        @Test
        @Order(10)
        void partnerCanUpdateRejectedReports() throws Exception {

            PendingMarriageDivorceReportRequest request = PendingMarriageDivorceReportRequest.builder()
                    .reportYear(ReportYear.Y2015)
                    .district(District.ALATAU)
                    .marriageCount(12)
                    .divorceCount(40)
                    .averageAge(BigDecimal.valueOf(24.8))
                    .source(Source.LOCAL_ADMINISTRATION)
                    .status(ReportStatus.PENDING)
                    .rejectionReason("")
                    .build();

            mockMvc.perform(post("/api/partner/reports/marriage-divorce")
                            .header("Authorization", "Bearer " + partnerToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());

            UUID reportId = pendingRepository.findAll().stream().filter(r -> r.getSource() == Source.LOCAL_ADMINISTRATION)
                    .findFirst()
                    .orElseThrow()
                    .getId();
            RejectionRequest rejection = new RejectionRequest();
            rejection.setRejectionReason("Invalid data");

            mockMvc.perform(post("/api/analyst/reports/marriage-divorce/" + reportId + "/reject")
                            .header("Authorization", "Bearer " + analystToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(rejection)))
                    .andExpect(status().isOk());

            PendingMarriageDivorceReportRequest updatedRequest = PendingMarriageDivorceReportRequest.builder()
                    .reportYear(ReportYear.Y2015)
                    .district(District.ALATAU)
                    .marriageCount(12)
                    .divorceCount(25)
                    .averageAge(BigDecimal.valueOf(24.8))
                    .source(Source.LOCAL_ADMINISTRATION)
                    .status(ReportStatus.PENDING)
                    .rejectionReason("")
                    .build();
            mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch("/api/partner/reports/marriage-divorce/" + reportId)
                    .header("Authorization", "Bearer " + partnerToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updatedRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Report updated and resubmitted"));
            var updatedReport = pendingRepository.findById(reportId).orElse(null);
            assertNotNull(updatedReport);
            assertEquals(12, updatedReport.getMarriageCount());
            assertEquals(ReportStatus.PENDING, updatedReport.getStatus());
            assertNull(updatedReport.getRejectionReason());

        }

        @Test
        @Order(11)
        void partnerShouldNotSeeOthersRejectedReports() throws Exception {
            LoginRequest partner2Request = new LoginRequest("partner2@example.com", "partner123");
            String partner2Response = mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(partner2Request)))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            String partner2Token = objectMapper.readTree(partner2Response).get("token").asText();

            PendingMarriageDivorceReportRequest request = PendingMarriageDivorceReportRequest.builder()
                    .reportYear(ReportYear.Y2020)
                    .district(District.NAURYZBAY)
                    .marriageCount(10)
                    .divorceCount(5)
                    .averageAge(BigDecimal.valueOf(31.0))
                    .source(Source.MINISTRY_OF_JUSTICE)
                    .status(ReportStatus.PENDING)
                    .rejectionReason("")
                    .build();

            mockMvc.perform(post("/api/partner/reports/marriage-divorce")
            .header("Authorization", "Bearer " + partner2Token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());

            UUID reportId = pendingRepository.findAll().stream().filter(r -> r.getSource() == Source.MINISTRY_OF_JUSTICE)
                    .findFirst()
                    .orElseThrow()
                    .getId();
            RejectionRequest rejection = new RejectionRequest();
            rejection.setRejectionReason("Invalid structure");

            mockMvc.perform(post("/api/analyst/reports/marriage-divorce/" + reportId + "/reject")
            .header("Authorization", "Bearer " + analystToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(rejection)))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/api/partner/reports/marriage-divorce/rejected")
                    .header("Authorization", "Bearer " + partnerToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[?(@.id == '" + reportId + "')]").doesNotExist());

        }

        @Test
        @Order(12)
        void partnerShouldNotUpdateOthersRejectedReports() throws Exception {

            LoginRequest partner2Request = new LoginRequest("partner2@example.com", "partner123");
            String partner2Response = mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(partner2Request)))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            String partner2Token = objectMapper.readTree(partner2Response).get("token").asText();

            PendingMarriageDivorceReportRequest request = PendingMarriageDivorceReportRequest.builder()
                    .reportYear(ReportYear.Y2020)
                    .district(District.NAURYZBAY)
                    .marriageCount(10)
                    .divorceCount(5)
                    .averageAge(BigDecimal.valueOf(31.0))
                    .source(Source.MINISTRY_OF_JUSTICE)
                    .status(ReportStatus.PENDING)
                    .rejectionReason("")
                    .build();

            mockMvc.perform(post("/api/partner/reports/marriage-divorce")
                            .header("Authorization", "Bearer " + partner2Token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());


            UUID reportId = pendingRepository.findAll().stream().filter(r -> r.getSource() == Source.MINISTRY_OF_JUSTICE)
                    .findFirst()
                    .orElseThrow()
                    .getId();
            RejectionRequest rejection = new RejectionRequest();
            rejection.setRejectionReason("Invalid info");

            mockMvc.perform(post("/api/analyst/reports/marriage-divorce/" + reportId + "/reject")
                            .header("Authorization", "Bearer " + analystToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(rejection)))
                    .andExpect(status().isOk());

            PendingMarriageDivorceReportRequest updatedRequest = PendingMarriageDivorceReportRequest.builder()
                    .reportYear(ReportYear.Y2020)
                    .district(District.NAURYZBAY)
                    .marriageCount(99)
                    .divorceCount(88)
                    .averageAge(BigDecimal.valueOf(31.0))
                    .source(Source.MINISTRY_OF_JUSTICE)
                    .status(ReportStatus.PENDING)
                    .rejectionReason("")
                    .build();

            mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch("/api/partner/reports/marriage-divorce/" + reportId)
            .header("Authorization", "Bearer " + partnerToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedRequest)))
                    .andExpect(status().isForbidden());
        }

}
