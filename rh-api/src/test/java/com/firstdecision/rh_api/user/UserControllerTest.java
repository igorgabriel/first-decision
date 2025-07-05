package com.firstdecision.rh_api.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstdecision.rh_api.user.dto.UserDTO;
import com.firstdecision.rh_api.user.model.User;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO buildDto(String name, String email, String password, String passwordConfirmation) {
        UserDTO dto = new UserDTO();
        dto.setName(name);
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setPasswordConfirmation(passwordConfirmation);
        return dto;
    }

    @Test
    void shouldCreateUser() throws Exception {
        UserDTO dto = buildDto("Alice", "alice@email.com", "senha123", "senha123");

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Alice")))
                .andExpect(jsonPath("$.email", is("alice@email.com")));
    }

    @Test
    void shouldListAllUsers() throws Exception {
        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(buildDto("Mario Augusto", "mario@email.com", "12345678", "12345678"))))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(buildDto("Camila Santos", "camila@email.com", "987654321", "987654321"))))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].email", is("mario@email.com")))
                .andExpect(jsonPath("$[1].email", is("camila@email.com")));
    }

    @Test
    void shouldFindById() throws Exception {
        String content = mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(buildDto("Carol Brandão", "carol@email.com", "senha1234", "senha1234"))))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        User user = objectMapper.readValue(content, User.class);

        // busca por ID
        mockMvc.perform(get("/v1/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Carol Brandão")))
                .andExpect(jsonPath("$.email", is("carol@email.com")));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        String json = mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(buildDto("Diego Amaral", "diego@email.com", "pw1234", "pw1234"))))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        User user = objectMapper.readValue(json, User.class);

        // atualiza nome e senha
        UserDTO update = buildDto("Diego Silva Amaral", "diego@email.com", "novaSenha", "novaSenha");

        mockMvc.perform(put("/v1/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Diego Silva Amaral")))
                .andExpect(jsonPath("$.email", is("diego@email.com")));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        String json = mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildDto("Evania Antunes", "eva@email.com",
                        "pass123", "pass123"))))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        User user = objectMapper.readValue(json, User.class);

        // deleta
        mockMvc.perform(delete("/v1/users/{id}", user.getId()))
                .andExpect(status().isNoContent());

        // confirma que não existe mais
        mockMvc.perform(get("/v1/users/{id}", user.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldValidateEmail() throws Exception {
       mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildDto("Cassia Nunes", "cassia.email.com",
                        "pass123", "pass123"))))
                .andExpect(status().isBadRequest());
    }

        @Test
    void shouldValidatePassword() throws Exception {
       mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildDto("Cassia Nunes", "cassia@email.com",
                        "pass123", "pass12356"))))
                .andExpect(status().isBadRequest());
    }
}
