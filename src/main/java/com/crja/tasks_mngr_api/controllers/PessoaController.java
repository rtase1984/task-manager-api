package com.crja.tasks_mngr_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crja.tasks_mngr_api.models.Pessoa;
import com.crja.tasks_mngr_api.services.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public Pessoa adicionarPessoa(@RequestBody Pessoa pessoa) {
        return pessoaService.salvarPessoa(pessoa);
    }

    @PutMapping("/{id}")
    public Pessoa alterarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        return pessoaService.atualizarPessoa(id, pessoa);
    }

    @DeleteMapping("/{id}")
    public void removerPessoa(@PathVariable Long id) {
        pessoaService.removerPessoa(id);
    }

    @GetMapping
    public List<Pessoa> listarPessoas() {
        return pessoaService.listarPessoas();
    }

    @GetMapping("/gastos")
    public List<Pessoa> buscarPorNome(@RequestParam String nome) {
        return pessoaService.buscarPorNome(nome);
    }
}