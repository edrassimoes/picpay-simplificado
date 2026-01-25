package br.com.edras.picpaysimplificado.controller;

import br.com.edras.picpaysimplificado.domain.enums.TransactionStatus;
import br.com.edras.picpaysimplificado.dto.transaction.TransactionRequestDTO;
import br.com.edras.picpaysimplificado.dto.transaction.TransactionResponseDTO;
import br.com.edras.picpaysimplificado.exception.transaction.MerchantCannotTransferException;
import br.com.edras.picpaysimplificado.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class TransactionControllerTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public TransactionService transactionService() {
            return Mockito.mock(TransactionService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionService transactionService;

    @Test
    void createTransaction_ShouldReturn201_WhenRequestIsValid() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO(100.0, 1L, 2L);
        TransactionResponseDTO response = new TransactionResponseDTO(
                1L, 1L, "Payer", 2L, "Payee", 100.0, LocalDateTime.now(), TransactionStatus.COMPLETED);

        when(transactionService.transfer(any(TransactionRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.payerId").value(1L))
                .andExpect(jsonPath("$.payeeId").value(2L));
    }

    @Test
    void createTransaction_ShouldReturn400_WhenAmountIsInvalid() throws Exception {
        String invalidRequest = """
        {
            "payerId": 1,
            "payeeId": 2,
            "amount": 0
        }
    """;

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTransaction_ShouldReturn403_WhenPayerIsMerchant() throws Exception {
        String request = """
        {
            "payerId": 1,
            "payeeId": 2,
            "amount": 100
        }
    """;

        when(transactionService.transfer(any(TransactionRequestDTO.class)))
                .thenThrow(new MerchantCannotTransferException(1L));

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isForbidden());
    }

}
