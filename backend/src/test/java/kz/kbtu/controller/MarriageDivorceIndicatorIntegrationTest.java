package kz.kbtu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import kz.kbtu.dto.auth.LoginRequest;
import kz.kbtu.repository.MarriageDivorceReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MarriageDivorceIndicatorIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private MarriageDivorceReportRepository repository;
    @Autowired private ObjectMapper objectMapper;

    private String analystToken;

    @BeforeEach
    void login() throws Exception {
        LoginRequest analystRequest = new LoginRequest("analyst@example.com", "analyst123");
        String analystResponse = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(analystRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        analystToken = objectMapper.readTree(analystResponse).get("token").asText();
    }

    @Test
    void shouldReturnMarriageCountIndicator() throws Exception {
        mockMvc.perform(get("/api/analyst/indicators/marriage-count-by-year")
                .header("Authorization", "Bearer " + analystToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    void shouldReturnIsForbidden() throws Exception {
        mockMvc.perform(get("/api/analyst/indicators/marriage-count-by-year"))
                .andExpect(status().isForbidden());
    }
}
