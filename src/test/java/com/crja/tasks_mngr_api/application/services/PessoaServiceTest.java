package com.crja.tasks_mngr_api.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


import com.crja.tasks_mngr_api.domain.models.Departamento;
import com.crja.tasks_mngr_api.domain.models.Pessoa;
import com.crja.tasks_mngr_api.domain.repository.DepartamentoRepository;
import com.crja.tasks_mngr_api.domain.repository.PessoaRepository;
import com.crja.tasks_mngr_api.infrastructure.dto.PessoaDTO;

@SpringBootTest
public class PessoaServiceTest {
   @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @InjectMocks
    private PessoaService pessoaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void adicionarPessoa_shouldAddPessoaSuccessfully() {
        // Arrange
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("João");

        Departamento departamento = new Departamento();
        departamento.setId(1L);

        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(pessoaRepository.save(any(Pessoa.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        PessoaDTO result = pessoaService.adicionarPessoa(pessoaDTO, 1L);

        // Assert
        assertNotNull(result);
        assertEquals("João", result.getNome());
        assertEquals(1L, result.getDepartamentoId());
    }


    
}