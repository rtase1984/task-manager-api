package com.crja.tasks_mngr_api.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crja.tasks_mngr_api.domain.models.Pessoa;
import com.crja.tasks_mngr_api.domain.repository.PessoaRepository;

@Repository
public interface PessoaRepositoryImpl extends PessoaRepository, JpaRepository<Pessoa, Long>{
    @Override
    @Query("SELECT p FROM Pessoa p JOIN p.tarefas t WHERE p.nome LIKE %:nome% AND t.prazo BETWEEN :dataInicio AND :dataFim")
    List<Pessoa> findByNomeAndTarefasPeriodo(@Param("nome") String nome,
                                             @Param("dataInicio") LocalDate dataInicio,
                                             @Param("dataFim") LocalDate dataFim);
                                             
    @Override
    @Query(value = "SELECT SUM(t.duracao) FROM Pessoa p JOIN p.tarefas t WHERE t.finalizado = TRUE AND p.id = :pessoaId GROUP BY p.id ")
    Integer totalHorasGastasPorPessoa(@Param("pessoaId") Long pessoaId);
}
