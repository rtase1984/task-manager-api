package com.crja.tasks_mngr_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crja.tasks_mngr_api.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    List<Pessoa> findByNomeContaining(String nome);
}
