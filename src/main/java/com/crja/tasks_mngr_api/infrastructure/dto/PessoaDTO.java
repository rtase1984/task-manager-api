package com.crja.tasks_mngr_api.infrastructure.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO {
    private Long id;
    private String nome;
    private Long departamentoId;
    private List<Long> tarefaIds;

}
