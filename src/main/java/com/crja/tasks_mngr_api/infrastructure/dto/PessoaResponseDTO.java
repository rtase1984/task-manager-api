package com.crja.tasks_mngr_api.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaResponseDTO {
    private String nome;
    private String departamento;
    private Integer totalHorasGastas;
}
