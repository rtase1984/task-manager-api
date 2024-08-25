package com.crja.tasks_mngr_api.infrastructure.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.crja.tasks_mngr_api.application.services.DepartamentoService;
import com.crja.tasks_mngr_api.infrastructure.dto.DepartamentoDTO;
import com.crja.tasks_mngr_api.infrastructure.dto.DepartamentoResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/departamentos")
@Tag(name = "Tarefas Controller", description = "Endpoints para a gestão de departamentos")
public class DepartamentoController {
    
    @Autowired
    private DepartamentoService departamentoService;

    @Operation(summary = "Criar um novo departamento", description = "Adiciona um novo departamento ao sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Departamento criado com sucesso", content = @Content(schema = @Schema(implementation = DepartamentoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<DepartamentoDTO> criarDepartamento(@RequestBody DepartamentoDTO departamentoDTO) {
        DepartamentoDTO criado = departamentoService.criarDepartamento(departamentoDTO);
        return ResponseEntity.status(201).body(criado);
    }

    @Operation(summary = "Atualizar um departamento existente", description = "Atualiza as informações de um departamento com base no ID fornecido.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Departamento atualizado com sucesso", content = @Content(schema = @Schema(implementation = DepartamentoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Departamento não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> atualizarDepartamento(@PathVariable Long id, @RequestBody DepartamentoDTO departamentoDTO) {
        DepartamentoDTO atualizado = departamentoService.atualizarDepartamento(id, departamentoDTO);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(summary = "Eliminar um departamento", description = "Elimina um departamento com base no ID fornecido.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Departamento eliminado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDepartamento(@PathVariable Long id) {
        departamentoService.eliminarDepartamento(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos os departamentos", description = "Recupera uma lista de todos os departamentos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de departamentos recuperada com sucesso", content = @Content(schema = @Schema(implementation = DepartamentoResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<DepartamentoResponseDTO>> listarDepartamentos() {
        List<DepartamentoResponseDTO> departamentos = departamentoService.obterResumoPorDepartamento();
        return ResponseEntity.ok(departamentos);
    }
    
}
