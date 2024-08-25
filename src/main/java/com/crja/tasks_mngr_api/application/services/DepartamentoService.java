package com.crja.tasks_mngr_api.application.services;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crja.tasks_mngr_api.domain.exceptions.ResourceNotFoundException;
import com.crja.tasks_mngr_api.domain.models.Departamento;
import com.crja.tasks_mngr_api.domain.repository.DepartamentoRepository;
import com.crja.tasks_mngr_api.infrastructure.dto.DepartamentoDTO;
import com.crja.tasks_mngr_api.infrastructure.dto.DepartamentoResponseDTO;

@Service
public class DepartamentoService {
    @Autowired
    private DepartamentoRepository departamentoRepository;

    public DepartamentoDTO criarDepartamento(DepartamentoDTO departamentoDTO) {
        Departamento departamento = new Departamento();
        departamento.setNome(departamentoDTO.getNome());
        Departamento departamentoCriado = departamentoRepository.save(departamento);
        return toDTO(departamentoCriado);
    }

    public DepartamentoDTO atualizarDepartamento(Long id, DepartamentoDTO departamentoDTO) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado"));

        departamento.setNome(departamentoDTO.getNome());
        Departamento departamentoAtualizado = departamentoRepository.save(departamento);
        return toDTO(departamentoAtualizado);
    }

     public void eliminarDepartamento(Long id) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado"));
        departamentoRepository.deleteById(departamento.getId());
    }

    public List<DepartamentoDTO> listarDepartamentos() {
        return departamentoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<DepartamentoResponseDTO> obterResumoPorDepartamento() {
        return departamentoRepository.findDepartamentoResumo();
    }

    private DepartamentoDTO toDTO(Departamento departamento){
        DepartamentoDTO dto = new DepartamentoDTO();
        //dto.setId(departamento.getId());
        dto.setNome(departamento.getNome());
        return dto;

    }

}
