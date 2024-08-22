package com.crja.tasks_mngr_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crja.tasks_mngr_api.models.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long>{
    
}
