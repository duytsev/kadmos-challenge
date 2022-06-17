package com.duitsev.savingsservice.api;

import com.duitsev.savingsservice.TestData;
import com.duitsev.savingsservice.api.dto.BalanceChangeDto;
import com.duitsev.savingsservice.domain.SavingsAccountRecord;
import com.duitsev.savingsservice.domain.SavingsAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.duitsev.savingsservice.TestData.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SavingsController.class)
public class SavingsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SavingsAccountService savingsAccountService;

    @Test
    public void shouldReturnBalanceOnGetBalance() throws Exception {
        when(savingsAccountService.getLastBalance()).thenReturn(savingsAccountRecord(BigDecimal.TEN));

        mockMvc.perform(get("/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(BigDecimal.TEN));
    }

    @Test
    public void shouldReturn404IfNotFoundExceptionWasThrown() throws Exception {
        when(savingsAccountService.getLastBalance()).thenThrow(new SavingsAccountRecordNotFoundException());

        mockMvc.perform(get("/balance"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnBalanceOnSaveBalance() throws Exception {
        var balance = new BigDecimal(-100);
        var objectMapper = new ObjectMapper();

        when(savingsAccountService.saveNewBalance(balance))
                .thenReturn(savingsAccountRecord(balance));

        mockMvc.perform(post("/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new BalanceChangeDto(balance))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(balance));
    }
}
