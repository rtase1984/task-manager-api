package com.crja.tasks_mngr_api.domain.repository;

import java.util.List;
import java.util.Optional;

import com.crja.tasks_mngr_api.domain.models.Departamento;
import com.crja.tasks_mngr_api.infrastructure.dto.DepartamentoResponseDTO;

public interface DepartamentoRepository {
    Optional<Departamento> findById(Long id);
    void deleteById(Long id);
    List<Departamento> findAll();
    Departamento save(Departamento departamento);
    List<DepartamentoResponseDTO> findDepartamentoResumo();
}