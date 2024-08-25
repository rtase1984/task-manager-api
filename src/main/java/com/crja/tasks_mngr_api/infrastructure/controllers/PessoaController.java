package com.crja.tasks_mngr_api.infrastructure.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crja.tasks_mngr_api.application.services.PessoaService;
import com.crja.tasks_mngr_api.domain.repository.DepartamentoRepository;
import com.crja.tasks_mngr_api.infrastructure.dto.PessoaDTO;
import com.crja.tasks_mngr_api.infrastructure.dto.PessoaResponseDTO;
import com.crja.tasks_mngr_api.infrastructure.dto.PessoaResponseMediaHorasGastasDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pessoas")
@Tag(name = "Pessoa Controller", description = "Endpoints para a gestão de pessoas")
public class PessoaController {

    private final PessoaService pessoaService;
    
    @Autowired
    public PessoaController(PessoaService pessoaService, DepartamentoRepository departamentoRepository) {
        this.pessoaService = pessoaService;
    }

    @Operation(summary = "Adicionar uma nova pessoa", description = "Adiciona uma nova pessoa ao sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso", content = @Content(schema = @Schema(implementation = PessoaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<PessoaDTO> adicionarPessoa(@RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO pessoaNova = pessoaService.adicionarPessoa(pessoaDTO, pessoaDTO.getDepartamentoId());
        return ResponseEntity.status(201).body(pessoaNova);
    }

    @Operation(summary = "Atualizar uma pessoa", description = "Atualiza as informações de uma pessoa existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso", content = @Content(schema = @Schema(implementation = PessoaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
   @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> atualizarPessoa(@PathVariable Long id, @RequestBody PessoaDTO pessoaDTO) {
            PessoaDTO pessoaAtualizada = pessoaService.atualizarPessoa(id, pessoaDTO);
            return ResponseEntity.ok(pessoaAtualizada);
    }

    @Operation(summary = "Eliminar uma pessoa", description = "Elimina uma pessoa do sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pessoa eliminada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPessoa(@PathVariable Long id) {
        pessoaService.eliminarPessoa(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todas as pessoas", description = "Recupera uma lista de todas as pessoas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pessoas recuperada com sucesso", content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<PessoaResponseDTO>> listarPessoas() {
        List<PessoaResponseDTO> pessoas = pessoaService.listarPessoas();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/gastos")
    @Operation(summary = "Buscar pessoas por nome e período",
               description = "Este endpoint busca pessoas por nome e período, e retorna a média de horas gastas por tarefa.")
    public ResponseEntity<List<PessoaResponseMediaHorasGastasDTO>> mediaHorasPessoasPorNomeEPeriodo(
        @RequestParam @Parameter(description = "Nome da pessoa") String nome,
        @RequestParam @Parameter(description = "Data de início no formato AAAA-MM-DD") String dataInicio,
        @RequestParam @Parameter(description = "Data de fim no formato AAAA-MM-DD") String dataFim) {

        List<PessoaResponseMediaHorasGastasDTO> pessoas = pessoaService.mediaHorasDePessoasPorNomeEPeriodo(nome, LocalDate.parse(dataInicio), LocalDate.parse(dataFim));
        return ResponseEntity.ok(pessoas);
    }
    
}