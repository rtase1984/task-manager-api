package com.crja.tasks_mngr_api.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crja.tasks_mngr_api.domain.exceptions.ResourceNotFoundException;
import com.crja.tasks_mngr_api.domain.models.Departamento;
import com.crja.tasks_mngr_api.domain.models.Pessoa;
import com.crja.tasks_mngr_api.domain.repository.DepartamentoRepository;
import com.crja.tasks_mngr_api.domain.repository.PessoaRepository;
import com.crja.tasks_mngr_api.infrastructure.dto.PessoaDTO;
import com.crja.tasks_mngr_api.infrastructure.dto.PessoaResponseDTO;
import com.crja.tasks_mngr_api.infrastructure.dto.PessoaResponseMediaHorasGastasDTO;


@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {
     @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @InjectMocks
    private PessoaService pessoaService;

    @BeforeEach
    void setUp() {
        pessoaService = new PessoaService(pessoaRepository, departamentoRepository);
    }

    @Test
    void whenPessoaIsAddedThenItShouldBeSaved() {
        // Arrange
        PessoaDTO pessoaDTO = new PessoaDTO();
        Long departamentoId = 1L;

        Departamento departamento = new Departamento();
        departamento.setId(departamentoId);

        Pessoa pessoaNova = new Pessoa();
        pessoaNova.setId(1L);
        pessoaNova.setDepartamento(departamento);

        when(departamentoRepository.findById(eq(departamentoId)))
                .thenReturn(Optional.of(departamento));
        when(pessoaRepository.save(any(Pessoa.class)))
                .thenReturn(pessoaNova);

        // Act
        PessoaDTO result = pessoaService.adicionarPessoa(pessoaDTO, departamentoId);

        // Assert
        assertNotNull(result);
        assertEquals(departamentoId, result.getDepartamentoId());
        verify(departamentoRepository, times(1)).findById(eq(departamentoId));
        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
    }

    @Test
    void whenPessoaIsUpdatedThenItShouldBeSaved() {
        // Arrange
        Long pessoaId = 1L;
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("Updated Name");
        pessoaDTO.setDepartamentoId(2L);

        Pessoa pessoaExistente = new Pessoa();
        pessoaExistente.setId(pessoaId);
        pessoaExistente.setNome("Old Name");

        Departamento departamento = new Departamento();
        departamento.setId(2L);

        when(pessoaRepository.findById(eq(pessoaId)))
                .thenReturn(Optional.of(pessoaExistente));
        when(departamentoRepository.findById(eq(2L)))
                .thenReturn(Optional.of(departamento));
        when(pessoaRepository.save(any(Pessoa.class)))
                .thenReturn(pessoaExistente);

        // Act
        PessoaDTO result = pessoaService.atualizarPessoa(pessoaId, pessoaDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getNome());
        assertEquals(2L, result.getDepartamentoId());
        verify(pessoaRepository, times(1)).findById(eq(pessoaId));
        verify(departamentoRepository, times(1)).findById(eq(2L));
        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
    }

    @Test
    void whenPessoaIsDeletedThenItShouldBeRemoved() {
        // Arrange
        Long pessoaId = 1L;
        when(pessoaRepository.findById(eq(pessoaId)))
                .thenReturn(Optional.of(new Pessoa()));

        // Act
        pessoaService.eliminarPessoa(pessoaId);

        // Assert
        verify(pessoaRepository, times(1)).findById(eq(pessoaId));
        verify(pessoaRepository, times(1)).deleteById(eq(pessoaId));
    }

    @Test
    void whenPessoaDoesNotExistThenItShouldThrowExceptionOnDelete() {
        // Arrange
        Long pessoaId = 1L;
        when(pessoaRepository.findById(eq(pessoaId)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> pessoaService.eliminarPessoa(pessoaId));
        verify(pessoaRepository, times(1)).findById(eq(pessoaId));
        verify(pessoaRepository, never()).deleteById(eq(pessoaId));
    }

    @Test
    void whenListPessoasCalledThenReturnListOfPessoas() {
        // Arrange
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L); // Asegúrate de establecer el ID para que coincida con el argumento en getTotalHorasGastas
        pessoa.setNome("Pessoa Name");
    
        Departamento departamento = new Departamento();
        departamento.setNome("Departamento Name");
        pessoa.setDepartamento(departamento);
    
        // Mocking the repository calls
        when(pessoaRepository.findAll())
                .thenReturn(Collections.singletonList(pessoa));
        when(pessoaRepository.totalHorasGastasPorPessoa(eq(1L))) // Usa eq(1L) para el ID específico
                .thenReturn(10);
    
        // Act
        List<PessoaResponseDTO> result = pessoaService.listarPessoas();
    
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        PessoaResponseDTO dto = result.get(0);
        assertEquals("Pessoa Name", dto.getNome());
        assertEquals("Departamento Name", dto.getDepartamento());
        assertEquals(10, dto.getTotalHorasGastas());
        verify(pessoaRepository, times(1)).findAll();
        verify(pessoaRepository, times(1)).totalHorasGastasPorPessoa(eq(1L)); // Verifica que el método fue llamado con el ID específico
    }

    @Test
    void whenBuscarPessoasPorNomeEPeriodoCalledThenReturnListOfPessoas() {
        // Arrange
        String nome = "Pessoa Name";
        LocalDate dataInicio = LocalDate.now().minusDays(10);
        LocalDate dataFim = LocalDate.now();
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);

        when(pessoaRepository.findByNomeAndTarefasPeriodo(eq(nome), eq(dataInicio), eq(dataFim)))
                .thenReturn(Collections.singletonList(pessoa));

        // Act
        List<PessoaDTO> result = pessoaService.buscarPessoasPorNomeEPeriodo(nome, dataInicio, dataFim);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        PessoaDTO dto = result.get(0);
        assertEquals(nome, dto.getNome());
        verify(pessoaRepository, times(1))
                .findByNomeAndTarefasPeriodo(eq(nome), eq(dataInicio), eq(dataFim));
    }

    @Test
    void whenGetTotalHorasGastasCalledThenReturnTotalHoras() {
        // Arrange
        Long pessoaId = 1L;
        when(pessoaRepository.totalHorasGastasPorPessoa(eq(pessoaId)))
                .thenReturn(15);

        // Act
        Integer result = pessoaService.getTotalHorasGastas(pessoaId);

        // Assert
        assertNotNull(result);
        assertEquals(15, result);
        verify(pessoaRepository, times(1)).totalHorasGastasPorPessoa(eq(pessoaId));
    }

    @Test
    void whenMediaHorasDePessoasPorNomeEPeriodoCalledThenReturnListOfPessoas() {
        // Arrange
        String nome = "Pessoa Name";
        LocalDate dataInicio = LocalDate.now().minusDays(10);
        LocalDate dataFim = LocalDate.now();
        PessoaResponseMediaHorasGastasDTO dto = new PessoaResponseMediaHorasGastasDTO();
        dto.setNome(nome);
        dto.setMediaHorasPorTarefa(5.0);

        when(pessoaRepository.mediaHorasDePessoasPorNomeEPeriodo(eq(nome), eq(dataInicio), eq(dataFim)))
                .thenReturn(Collections.singletonList(dto));

        // Act
        List<PessoaResponseMediaHorasGastasDTO> result = pessoaService.mediaHorasDePessoasPorNomeEPeriodo(nome, dataInicio, dataFim);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        PessoaResponseMediaHorasGastasDTO resultDto = result.get(0);
        assertEquals(nome, resultDto.getNome());
        assertEquals(5.0, resultDto.getMediaHorasPorTarefa());
        verify(pessoaRepository, times(1))
                .mediaHorasDePessoasPorNomeEPeriodo(eq(nome), eq(dataInicio), eq(dataFim));
    }




    
}