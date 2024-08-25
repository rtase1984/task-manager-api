package com.crja.tasks_mngr_api.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.crja.tasks_mngr_api.domain.models.Pessoa;
import com.crja.tasks_mngr_api.infrastructure.dto.PessoaResponseMediaHorasGastasDTO;

public interface PessoaRepository {
    Pessoa save(Pessoa pessoa);
    Optional<Pessoa> findById(Long id);
    void deleteById(Long id);
    List<Pessoa> findAll();
    List<Pessoa> findByNomeAndTarefasPeriodo(String nome, LocalDate dataInicio, LocalDate dataFim);
    Integer totalHorasGastasPorPessoa(Long id);
    List<PessoaResponseMediaHorasGastasDTO> mediaHorasDePessoasPorNomeEPeriodo(@Param("nome") String nome, 
                                                 @Param("dataInicio") LocalDate dataInicio, 
                                                 @Param("dataFim") LocalDate dataFim);

}
