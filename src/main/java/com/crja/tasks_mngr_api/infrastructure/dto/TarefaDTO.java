package com.crja.tasks_mngr_api.infrastructure.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate plazo;
    private Integer duracion;
    private Long departamentoId;
    private Long pessoaId;
    private boolean finalizado;

}
