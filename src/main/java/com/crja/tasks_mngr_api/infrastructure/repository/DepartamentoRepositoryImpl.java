package com.crja.tasks_mngr_api.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crja.tasks_mngr_api.domain.models.Departamento;

public interface DepartamentoRepositoryImpl extends JpaRepository<Departamento, Long>{
    
}
