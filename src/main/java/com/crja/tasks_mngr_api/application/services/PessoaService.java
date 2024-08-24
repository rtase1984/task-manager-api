package com.crja.tasks_mngr_api.application.services;

import org.springframework.stereotype.Service;

import com.crja.tasks_mngr_api.domain.exceptions.ResourceNotFoundException;
import com.crja.tasks_mngr_api.domain.models.Departamento;
import com.crja.tasks_mngr_api.domain.models.Pessoa;
import com.crja.tasks_mngr_api.domain.repository.DepartamentoRepository;
import com.crja.tasks_mngr_api.domain.repository.PessoaRepository;
import com.crja.tasks_mngr_api.domain.repository.TarefaRepository;
import com.crja.tasks_mngr_api.infrastructure.dto.PessoaDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PessoaService {


    private final PessoaRepository pessoaRepository;
    private final DepartamentoRepository departamentoRepository;

    public PessoaService(PessoaRepository pessoaRepository, DepartamentoRepository departamentoRepository, TarefaRepository tarefaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.departamentoRepository = departamentoRepository;

    }

    public PessoaDTO adicionarPessoa(PessoaDTO pessoaDTO, Long departamentoId) {
        Pessoa pessoa = new Pessoa();
        Departamento departamento = departamentoRepository.findById(departamentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado"));
        pessoa.setDepartamento(departamento);
        Pessoa pessoaNova = pessoaRepository.save(pessoa);
        
        return toDTO(pessoaNova);
    }

    public PessoaDTO atualizarPessoa(Long id, PessoaDTO pessoaDTO) {
        Pessoa pessoaExistente = pessoaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));
    
        pessoaExistente.setNome(pessoaDTO.getNome());
        Optional<Departamento> departamento = departamentoRepository.findById(pessoaDTO.getDepartamentoId());
        if(departamento.get() != pessoaExistente.getDepartamento()){
            pessoaExistente.setDepartamento(departamento.get());
        }
        Pessoa pessoaAtualizada = pessoaRepository.save(pessoaExistente);
        
        return toDTO(pessoaAtualizada);
    }

    public void eliminarPessoa(Long id) {
        if (!pessoaRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Pessoa não encontrada");
        }
        pessoaRepository.deleteById(id);
    }

    public List<PessoaDTO> listarPessoas() {
        return pessoaRepository.findAll().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
    }

    public List<PessoaDTO> buscarPessoasPorNomeEPeriodo(String nome, LocalDate dataInicio, LocalDate dataFim) {
        List<Pessoa> pessoas = pessoaRepository.findByNomeAndTarefasPeriodo(nome, dataInicio, dataFim);
        
        return pessoas.stream().map(this::toDTO)
                               .collect(Collectors.toList());
    }

    private PessoaDTO toDTO(Pessoa pessoa){
        PessoaDTO dto = new PessoaDTO();
        dto.setId(pessoa.getId());
        dto.setNome(pessoa.getNome());
        dto.setDepartamentoId(pessoa.getDepartamento().getId());
        List<Long> tarefaIds = pessoa.getTarefas().stream()
                                    .map(tarefa -> tarefa.getId())
                                    .collect(Collectors.toList());
        dto.setTarefaIds(tarefaIds);
        return dto;
    }

}

