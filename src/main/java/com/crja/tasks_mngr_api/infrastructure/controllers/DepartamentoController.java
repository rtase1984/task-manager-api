package com.crja.tasks_mngr_api.infrastructure.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {
    
    @Autowired
    private DepartamentoService departamentoService;

    @PostMapping
    public ResponseEntity<DepartamentoDTO> criarDepartamento(@RequestBody DepartamentoDTO departamentoDTO) {
        DepartamentoDTO criado = departamentoService.criarDepartamento(departamentoDTO);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> atualizarDepartamento(@PathVariable Long id, @RequestBody DepartamentoDTO departamentoDTO) {
        DepartamentoDTO atualizado = departamentoService.atualizarDepartamento(id, departamentoDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDepartamento(@PathVariable Long id) {
        departamentoService.eliminarDepartamento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DepartamentoResponseDTO>> listarDepartamentos() {
        List<DepartamentoResponseDTO> departamentos = departamentoService.obterResumoPorDepartamento();
        return ResponseEntity.ok(departamentos);
    }
    
}
