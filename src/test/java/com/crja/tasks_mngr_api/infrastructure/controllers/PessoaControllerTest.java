package com.crja.tasks_mngr_api.infrastructure.controllers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.crja.tasks_mngr_api.application.services.PessoaService;
import com.crja.tasks_mngr_api.domain.exceptions.ResourceNotFoundException;
import com.crja.tasks_mngr_api.domain.models.Departamento;
import com.crja.tasks_mngr_api.domain.models.Pessoa;
import com.crja.tasks_mngr_api.domain.models.Tarefa;
import com.crja.tasks_mngr_api.domain.repository.DepartamentoRepository;
import com.crja.tasks_mngr_api.infrastructure.dto.PessoaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PessoaController.class)
public class PessoaControllerTest {

    @InjectMocks
    private PessoaController pessoaController;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void adicionarPessoa_shouldReturnCreatedPessoa() throws Exception {
        // Arrange
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("João");
        pessoaDTO.setDepartamentoId(1L);

        when(pessoaService.adicionarPessoa(any(PessoaDTO.class), anyLong())).thenReturn(pessoaDTO);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"João\",\"departamentoId\":1}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", is("João")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departamentoId", is(1)));
    }

}