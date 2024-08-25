package com.crja.tasks_mngr_api.application.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.crja.tasks_mngr_api.domain.models.Tarefa;
import com.crja.tasks_mngr_api.domain.repository.DepartamentoRepository;
import com.crja.tasks_mngr_api.domain.repository.PessoaRepository;
import com.crja.tasks_mngr_api.domain.repository.TarefaRepository;
import com.crja.tasks_mngr_api.infrastructure.dto.TarefaDTO;



@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @InjectMocks
    private TarefaService tarefaService;

    @BeforeEach
    void setUp() {
        tarefaService = new TarefaService(tarefaRepository, pessoaRepository, departamentoRepository);
    }
   @Test
    void whenTrafeInformedThenItShouldBeCreated(){

        // Arrange
        TarefaDTO tarefaDTO = new TarefaDTO();
        tarefaDTO.setDepartamentoId(1L);
        tarefaDTO.setPessoaId(1L);

        Departamento departamento = new Departamento();
        departamento.setId(1L);

        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);


        Tarefa tarefaSalva = new Tarefa();
        tarefaSalva.setId(1L);
        tarefaSalva.setDepartamento(departamento);
        tarefaSalva.setPessoaAlocada(pessoa);

        when(departamentoRepository.findById(eq(1L))).thenReturn(Optional.of(departamento));
        when(pessoaRepository.findById(eq(1L))).thenReturn(Optional.of(pessoa));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefaSalva);

        // Act
        TarefaDTO result = tarefaService.adicionarTarefa(tarefaDTO);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getDepartamentoId());
        assertNotNull(result.getPessoaId());

        verify(departamentoRepository, times(1)).findById(eq(1L));
        verify(pessoaRepository, times(1)).findById(eq(1L));
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));


    }

    @Test
    void whenPessoaAndTarefaExistAndBelongToSameDepartamentoThenPessoaShouldBeAllocated() {
        // Arrange
        Long tarefaId = 1L;
        Long pessoaId = 1L;
        
        Departamento departamento = new Departamento();
        departamento.setId(1L);
        
        Pessoa pessoa = new Pessoa();
        pessoa.setId(pessoaId);
        pessoa.setDepartamento(departamento);
        
        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);
        tarefa.setDepartamento(departamento);

        when(tarefaRepository.findById(eq(tarefaId))).thenReturn(Optional.of(tarefa));
        when(pessoaRepository.findById(eq(pessoaId))).thenReturn(Optional.of(pessoa));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        // Act
        TarefaDTO result = tarefaService.alocarPessoaNaTarefa(tarefaId, pessoaId);

        // Assert
        assertNotNull(result);
        verify(tarefaRepository, times(1)).findById(eq(tarefaId));
        verify(pessoaRepository, times(1)).findById(eq(pessoaId));
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    void whenTarefaExistsThenItShouldBeFinalized() {
        // Arrange
        Long tarefaId = 1L;
        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);
        tarefa.setFinalizado(false);

        when(tarefaRepository.findById(eq(tarefaId))).thenReturn(Optional.of(tarefa));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        // Act
        TarefaDTO result = tarefaService.finalizarTarefa(tarefaId);

        // Assert
        assertNotNull(result);
        verify(tarefaRepository, times(1)).findById(eq(tarefaId));
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    void shouldReturnListOfPendingTarefas() {
        // Arrange
        Tarefa tarefa1 = new Tarefa();
        Tarefa tarefa2 = new Tarefa();
        Tarefa tarefa3 = new Tarefa();

        when(tarefaRepository.findTop3ByPessoaAlocadaIsNullOrderByPrazoAsc()).thenReturn(List.of(tarefa1, tarefa2, tarefa3));

        // Act
        List<TarefaDTO> result = tarefaService.listarTarefasPendentes();

        // Assert
        assertNotNull(result);
        verify(tarefaRepository, times(1)).findTop3ByPessoaAlocadaIsNullOrderByPrazoAsc();
    }

    @Test
    void shouldReturnListOfAllTarefas() {
        // Arrange
        Tarefa tarefa1 = new Tarefa();
        Tarefa tarefa2 = new Tarefa();
        
        when(tarefaRepository.findAll()).thenReturn(List.of(tarefa1, tarefa2));

        // Act
        List<TarefaDTO> result = tarefaService.listarTarefasTodas();

        // Assert
        assertNotNull(result);
        verify(tarefaRepository, times(1)).findAll();
    }

    @Test
    void whenDepartamentoNotFoundThenThrowResourceNotFoundException() {
        // Arrange
        TarefaDTO tarefaDTO = new TarefaDTO();
        tarefaDTO.setDepartamentoId(1L);
        tarefaDTO.setPessoaId(1L);

        when(departamentoRepository.findById(eq(1L))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> tarefaService.adicionarTarefa(tarefaDTO));

        verify(departamentoRepository, times(1)).findById(eq(1L));
        verify(pessoaRepository, times(0)).findById(any(Long.class));
        verify(tarefaRepository, times(0)).save(any(Tarefa.class));
    }







   
}

