package com.crja.tasks_mngr_api.infrastructure.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crja.tasks_mngr_api.application.services.TarefaService;
import com.crja.tasks_mngr_api.infrastructure.dto.TarefaDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/tarefas")
@Tag(name = "Tarefas Controller", description = "Endpoints para a gestão de tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Operation(summary = "Adicionar uma nova tarefa", description = "Adiciona uma nova tarefa ao sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso", content = @Content(schema = @Schema(implementation = TarefaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<TarefaDTO> adicionarTarefa(@RequestBody TarefaDTO tarefaDTO) {
        TarefaDTO tarefaNova = tarefaService.adicionarTarefa(tarefaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaNova);
    }

    @Operation(summary = "Alocar uma pessoa a uma tarefa", description = "Aloca uma pessoa existente a uma tarefa.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa alocada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/alocar/{id}")
    public ResponseEntity<?> alocarPessoa(@PathVariable Long id, @RequestParam Long pessoaId) {
        try {
            tarefaService.alocarPessoaNaTarefa(id, pessoaId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Erro interno no servidor.");
        }

    }

    @Operation(summary = "Finalizar uma tarefa", description = "Finaliza uma tarefa existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarefa finalizada com sucesso", content = @Content(schema = @Schema(implementation = TarefaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @PutMapping("/finalizar/{id}")
    public ResponseEntity<TarefaDTO> finalizarTarefa(@PathVariable Long id) {
        TarefaDTO tarefaAtualizada = new TarefaDTO();
        tarefaAtualizada = tarefaService.finalizarTarefa(id);
        return ResponseEntity.ok(tarefaAtualizada);
    }

    @Operation(summary = "Listar tarefas pendentes", description = "Recupera uma lista de todas as tarefas pendentes.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tarefas pendentes recuperada com sucesso", content = @Content(schema = @Schema(implementation = TarefaDTO.class)))
    })
    @GetMapping("/pendentes")
    public ResponseEntity<List<TarefaDTO>> listarTarefasPendentes() {
        List<TarefaDTO> tarefasPendentes = tarefaService.listarTarefasPendentes();
        return ResponseEntity.ok(tarefasPendentes);
    }

    @Operation(summary = "Listar todas as tarefas", description = "Recupera uma lista de todas as tarefas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tarefas recuperada com sucesso", content = @Content(schema = @Schema(implementation = TarefaDTO.class)))
    })
    @GetMapping
    public ResponseEntity<?> listarTarefasTodas() {
        List<TarefaDTO> tarefas = tarefaService.listarTarefasTodas();
        if (tarefas.isEmpty()) {
            return ResponseEntity.ok("No hay tarefas disponibles."); // Devuelve un mensaje cuando la lista está vacía
        }
        
        return ResponseEntity.ok(tarefas);
    }

}
