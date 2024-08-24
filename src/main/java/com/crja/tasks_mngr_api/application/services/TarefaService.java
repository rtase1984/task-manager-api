package com.crja.tasks_mngr_api.application.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.crja.tasks_mngr_api.domain.exceptions.ResourceNotFoundException;
import com.crja.tasks_mngr_api.domain.models.Departamento;
import com.crja.tasks_mngr_api.domain.models.Pessoa;
import com.crja.tasks_mngr_api.domain.models.Tarefa;
import com.crja.tasks_mngr_api.domain.repository.DepartamentoRepository;
import com.crja.tasks_mngr_api.domain.repository.PessoaRepository;
import com.crja.tasks_mngr_api.domain.repository.TarefaRepository;
import com.crja.tasks_mngr_api.infrastructure.dto.TarefaDTO;

@Service
public class TarefaService {
   
    private final TarefaRepository tarefaRepository;
    private final PessoaRepository pessoaRepository;
    private final DepartamentoRepository departamentoRepository;


    public TarefaService(TarefaRepository tarefaRepository, PessoaRepository pessoaRepository,
            DepartamentoRepository departamentoRepository) {
        this.tarefaRepository = tarefaRepository;
        this.pessoaRepository = pessoaRepository;
        this.departamentoRepository = departamentoRepository;
    }

    public TarefaDTO adicionarTarefa(TarefaDTO tarefaDTO) {
        
        Tarefa tarefaNova = new Tarefa();
        Departamento departamento = departamentoRepository.findById(tarefaDTO.getDepartamentoId())
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado"));
        tarefaNova.setDepartamento(departamento);

        Pessoa pessoa = pessoaRepository.findById(tarefaDTO.getPessoaId())
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado"));
        tarefaNova.setPessoaAlocada(pessoa);

        tarefaRepository.save(tarefaNova);
        return toDTO(tarefaNova);
    }

    public TarefaDTO alocarPessoaNaTarefa(Long tarefaId, Long pessoaId) {
        Tarefa tarefa = tarefaRepository.findById(tarefaId).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));
        
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado"));
        
        if (!pessoa.getDepartamento().equals(tarefa.getDepartamento())) {
            throw new IllegalArgumentException("A pessoa não pertence ao departamento da tarefa.");
        }
        tarefa.setPessoaAlocada(pessoa);
        
        Tarefa tarefaActualizada = tarefaRepository.save(tarefa);
        return toDTO(tarefaActualizada);
    }

    public TarefaDTO finalizarTarefa(Long tarefaId) {
        Tarefa tarefa = tarefaRepository.findById(tarefaId).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));
        tarefa.setFinalizado(true);

        Tarefa tarefaActualizada = tarefaRepository.save(tarefa);

        return toDTO(tarefaActualizada);
    }

    public List<TarefaDTO> listarTarefasPendentes() {
        List<Tarefa> tarefasPendentes = tarefaRepository.findTop3ByPessoaIsNullOrderByPrazoAsc();
        return tarefasPendentes.stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList());
    }

    public List<TarefaDTO> listarTarefasTodas() {
        List<Tarefa> tarefas = tarefaRepository.findAll();
        return tarefas.stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList());
    }

    private TarefaDTO toDTO(Tarefa tarefa){
        TarefaDTO dto = new TarefaDTO();
        dto.setDepartamentoId(tarefa.getDepartamento().getId());
        dto.setDescripcion(tarefa.getDescricao());
        dto.setDuracion(tarefa.getDuracao());
        dto.setPlazo(tarefa.getPrazo());
        dto.setTitulo(tarefa.getTitulo());
        dto.setPessoaId(tarefa.getPessoaAlocada().getId());

        return dto;
    }
}
