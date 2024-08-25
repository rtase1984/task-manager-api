package com.crja.tasks_mngr_api.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crja.tasks_mngr_api.domain.models.Tarefa;
import com.crja.tasks_mngr_api.domain.repository.TarefaRepository;

@Repository
public interface TarefaRepositoryImpl extends JpaRepository<Tarefa, Long>, TarefaRepository {
    @Override
    List<Tarefa> findTop3ByPessoaAlocadaIsNullOrderByPrazoAsc();
}