package com.crja.tasks_mngr_api.domain.repository;

import java.util.List;
import java.util.Optional;

import com.crja.tasks_mngr_api.domain.models.Tarefa;

public interface TarefaRepository {
    Tarefa save(Tarefa tarefa);
    Optional<Tarefa> findById(Long id);
    List<Tarefa> findTop3ByPessoaIsNullOrderByPrazoAsc();
    List<Tarefa> findAll();

}
