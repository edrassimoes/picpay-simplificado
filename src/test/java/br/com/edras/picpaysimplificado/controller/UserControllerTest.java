package br.com.edras.picpaysimplificado.controller;

import br.com.edras.picpaysimplificado.domain.CommonUser;
import br.com.edras.picpaysimplificado.dto.user.UserRequestDTO;
import br.com.edras.picpaysimplificado.dto.user.UserResponseDTO;
import br.com.edras.picpaysimplificado.fixtures.CommonUserFixtures;
import br.com.edras.picpaysimplificado.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class) // Simula um contexto web, ignorando a camada de segurança.
@Import(UserControllerTest.TestConfig.class)
public class UserControllerTest {

    /*

    @MockBean
    private UserService userService;

     */

    // Substitui o @MockBean.
    @TestConfiguration
    static class TestConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc; // Simula um cliente http.

    @Autowired
    private ObjectMapper objectMapper; // Traduz o Objeto fornecido em JSON.

    @Autowired
    private UserService userService;

    @Test
    public void createUser_WithValidData_ReturnsCreated() throws Exception {
        CommonUser user = CommonUserFixtures.createValidCommonUser();
        UserRequestDTO userRequestDTO = new UserRequestDTO(user);
        UserResponseDTO userResponseDTO = new UserResponseDTO(user);

        when(userService.createUser(any(UserRequestDTO.class))).thenReturn(userResponseDTO);

        mockMvc.perform(post("/users").content(objectMapper.writeValueAsString(userRequestDTO)).contentType("application/json"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(userResponseDTO.getId())))
                .andExpect(jsonPath("$.name", is(userResponseDTO.getName())))
                .andExpect(jsonPath("$.userType", is(userResponseDTO.getUserType().toString())));
    }

}