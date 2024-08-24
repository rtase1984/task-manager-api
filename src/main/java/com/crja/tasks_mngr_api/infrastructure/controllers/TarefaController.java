package com.crja.tasks_mngr_api.infrastructure.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefaDTO> adicionarTarefa(@RequestBody TarefaDTO tarefaDTO) {
        TarefaDTO tarefaNova = tarefaService.adicionarTarefa(tarefaDTO);
        return ResponseEntity.status(201).body(tarefaNova);
    }

    @PutMapping("/alocar/{id}")
    public ResponseEntity<TarefaDTO> alocarPessoa(@PathVariable Long id, @RequestParam Long pessoaId) {
        TarefaDTO tarefaAtualizada = new TarefaDTO();
        tarefaAtualizada = tarefaService.alocarPessoaNaTarefa(id, pessoaId);
        return ResponseEntity.ok(tarefaAtualizada);
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<TarefaDTO> finalizarTarefa(@PathVariable Long id) {
        TarefaDTO tarefaAtualizada = new TarefaDTO();
        tarefaAtualizada = tarefaService.finalizarTarefa(id);
        return ResponseEntity.ok(tarefaAtualizada);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<TarefaDTO>> listarTarefasPendentes() {
        List<TarefaDTO> tarefasPendentes = tarefaService.listarTarefasPendentes();
        return ResponseEntity.ok(tarefasPendentes);
    }

    @GetMapping
    public ResponseEntity<?> listarTarefasTodas() {
        List<TarefaDTO> tarefas = tarefaService.listarTarefasTodas();
        if (tarefas.isEmpty()) {
            return ResponseEntity.ok("No hay tarefas disponibles."); // Devuelve un mensaje cuando la lista está vacía
        }
        
        return ResponseEntity.ok(tarefas);
    }
}
