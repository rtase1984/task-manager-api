package com.crja.tasks_mngr_api.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.crja.tasks_mngr_api.domain.models.Pessoa;

public interface PessoaRepository {
    Pessoa save(Pessoa pessoa);
    Optional<Pessoa> findById(Long id);
    void deleteById(Long id);
    List<Pessoa> findAll();
    List<Pessoa> findByNomeAndTarefasPeriodo(String nome, LocalDate dataInicio, LocalDate dataFim);
    Integer totalHorasGastasPorPessoa(Long id);

}
