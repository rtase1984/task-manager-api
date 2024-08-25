package com.crja.tasks_mngr_api.application.services;

import org.springframework.stereotype.Service;

import com.crja.tasks_mngr_api.domain.exceptions.ResourceNotFoundException;
import com.crja.tasks_mngr_api.domain.models.Departamento;
import com.crja.tasks_mngr_api.domain.models.Pessoa;
import com.crja.tasks_mngr_api.domain.repository.DepartamentoRepository;
import com.crja.tasks_mngr_api.domain.repository.PessoaRepository;
import com.crja.tasks_mngr_api.domain.repository.TarefaRepository;
import com.crja.tasks_mngr_api.infrastructure.dto.PessoaDTO;
import com.crja.tasks_mngr_api.infrastructure.dto.PessoaResponseDTO;
import com.crja.tasks_mngr_api.infrastructure.dto.PessoaResponseMediaHorasGastasDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PessoaService {


    private final PessoaRepository pessoaRepository;
    private final DepartamentoRepository departamentoRepository;

    public PessoaService(PessoaRepository pessoaRepository, DepartamentoRepository departamentoRepository) {
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

    public List<PessoaResponseDTO> listarPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        List<PessoaResponseDTO> responseDTOList = new ArrayList<>();

        for (Pessoa pessoa : pessoas) {
            Integer totalHorasGastas = this.getTotalHorasGastas(pessoa.getId());
            
            
            PessoaResponseDTO responseDTO = new PessoaResponseDTO(
                pessoa.getNome(),
                pessoa.getDepartamento().getNome(),
                totalHorasGastas
            );
            responseDTOList.add(responseDTO);
        }

        return responseDTOList;
    }

    public List<PessoaDTO> buscarPessoasPorNomeEPeriodo(String nome, LocalDate dataInicio, LocalDate dataFim) {
        List<Pessoa> pessoas = pessoaRepository.findByNomeAndTarefasPeriodo(nome, dataInicio, dataFim);
        
        return pessoas.stream().map(this::toDTO)
                               .collect(Collectors.toList());
    }

    public Integer getTotalHorasGastas(Long pessoaId) {
        Integer totalHoras = pessoaRepository.totalHorasGastasPorPessoa(pessoaId);
        return totalHoras != null ? totalHoras : 0;
    }

    public List<PessoaResponseMediaHorasGastasDTO> mediaHorasDePessoasPorNomeEPeriodo(String nome, LocalDate dataInicio, LocalDate dataFim){
        List<PessoaResponseMediaHorasGastasDTO> resultado = pessoaRepository.mediaHorasDePessoasPorNomeEPeriodo(nome, dataInicio, dataFim);
        return resultado != null ? resultado : Collections.emptyList();
    }

    private PessoaDTO toDTO(Pessoa pessoa) {
        PessoaDTO dto = new PessoaDTO();
        dto.setId(pessoa.getId());
        dto.setNome(pessoa.getNome());
        
        if (pessoa.getDepartamento() != null) {
            dto.setDepartamentoId(pessoa.getDepartamento().getId());
        } else {
            dto.setDepartamentoId(null); // O maneja el caso de otra manera si es necesario
        }
        
        List<Long> tarefaIds = pessoa.getTarefas().stream()
                                    .map(tarefa -> tarefa.getId())
                                    .collect(Collectors.toList());
        dto.setTarefaIds(tarefaIds);
        return dto;
    }

}

