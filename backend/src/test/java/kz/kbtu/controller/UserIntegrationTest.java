package kz.kbtu.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.kbtu.dto.auth.LoginRequest;
import kz.kbtu.dto.auth.RegisterRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.stream.StreamSupport;

@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String token;
    private static String userId;
    @BeforeAll
    void adminLogin() throws Exception{
        LoginRequest loginRequest = new LoginRequest("admin@example.com","admin123");

        String loginResponse = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        token = objectMapper.readTree(loginResponse).get("token").asText();

    }


    @Test
    @Order(1)
    void registerUser_shouldReturn200() throws Exception {

        RegisterRequest request = new RegisterRequest();
        request.setName("integration anlayst");
        request.setEmail("int_analyst@example.com");
        request.setPassword("analyst123");
        request.setRole("analyst");
        String registerResponse = mockMvc.perform(post("/api/admin/register")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

    }

    @Test
    @Order(2)
    void showUsers_shouldReturn200() throws Exception {
        MvcResult getUsers = mockMvc.perform(get("/api/admin/users")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode usersArray = objectMapper.readTree(getUsers.getResponse().getContentAsString());
        JsonNode matchedUser = StreamSupport.stream(usersArray.spliterator(), false)
                .filter(user -> user.get("email").asText().equals("int_analyst@example.com"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        userId = matchedUser.get("id").asText();
    }

    @Test
    @Order(3)
    void deleteUser_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/api/admin/users/delete/" + userId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
