package com.crja.tasks_mngr_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crja.tasks_mngr_api.models.Tarefa;
import com.crja.tasks_mngr_api.services.TarefaService;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public Tarefa adicionarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaService.salvarTarefa(tarefa);
    }

    @PutMapping("/alocar/{id}")
    public Tarefa alocarPessoa(@PathVariable Long id, @RequestParam Long pessoaId) {
        return tarefaService.alocarPessoaNaTarefa(id, pessoaId);
    }

    @PutMapping("/finalizar/{id}")
    public Tarefa finalizarTarefa(@PathVariable Long id) {
        return tarefaService.finalizarTarefa(id);
    }

    @GetMapping("/pendentes")
    public List<Tarefa> listarTarefasPendentes() {
        return tarefaService.listarTarefasPendentes();
    }
}
