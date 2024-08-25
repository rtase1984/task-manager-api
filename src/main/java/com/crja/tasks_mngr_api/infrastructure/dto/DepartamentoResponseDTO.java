package com.crja.tasks_mngr_api.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentoResponseDTO {
    private String nome;
    private Long quantidadeTarefas;
    private Long quantidadePessoas;
}
