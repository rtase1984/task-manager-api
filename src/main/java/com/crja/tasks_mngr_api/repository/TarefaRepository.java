package com.crja.tasks_mngr_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crja.tasks_mngr_api.models.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findTop3ByPessoaAlocadaIsNullOrderByPrazoAsc();
    
}
