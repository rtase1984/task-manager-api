package com.crja.tasks_mngr_api.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PessoaResponseMediaHorasGastasDTO {
    private String nome;
    private Long horasTrabalhadas;
    private Double mediaHorasPorTarefa;

}
