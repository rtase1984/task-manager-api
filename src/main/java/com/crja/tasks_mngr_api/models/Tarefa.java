package com.crja.tasks_mngr_api.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;

    private LocalDate prazo;

    @ManyToOne
    private Departamento departamento;

    private int duracao;

    @ManyToOne
    private Pessoa pessoaAlocada;

    private boolean finalizado;
}
