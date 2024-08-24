package com.crja.tasks_mngr_api.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crja.tasks_mngr_api.domain.models.Tarefa;
import com.crja.tasks_mngr_api.domain.repository.TarefaRepository;

@Repository
public interface TarefaRepositoryImpl extends TarefaRepository, JpaRepository<Tarefa, Long> {
    @Override
    List<Tarefa> findTop3ByPessoaIsNullOrderByPrazoAsc();
}