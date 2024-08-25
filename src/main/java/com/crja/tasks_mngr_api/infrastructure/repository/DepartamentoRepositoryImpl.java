package com.crja.tasks_mngr_api.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crja.tasks_mngr_api.domain.models.Departamento;
import com.crja.tasks_mngr_api.domain.repository.DepartamentoRepository;
import com.crja.tasks_mngr_api.infrastructure.dto.DepartamentoResponseDTO;

@Repository
public interface DepartamentoRepositoryImpl extends JpaRepository<Departamento, Long>, DepartamentoRepository{

    @Query("SELECT  new com.crja.tasks_mngr_api.infrastructure.dto.DepartamentoResponseDTO(d.nome, " + 
           "COUNT(DISTINCT t.id), " +
           "COUNT(DISTINCT p.id))  " +
           "FROM  Departamento d " +
           "LEFT JOIN d.pessoas p " +
           "LEFT JOIN d.tarefas t " +
           "GROUP BY d.nome")
    List<DepartamentoResponseDTO> findDepartamentoResumo();
    
}
