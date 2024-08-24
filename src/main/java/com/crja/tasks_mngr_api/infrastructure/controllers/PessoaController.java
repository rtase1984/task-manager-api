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


@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;
    
    @Autowired
    public PessoaController(PessoaService pessoaService, DepartamentoRepository departamentoRepository) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> adicionarPessoa(@RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO pessoaNova = pessoaService.adicionarPessoa(pessoaDTO, pessoaDTO.getDepartamentoId());
        return ResponseEntity.status(201).body(pessoaNova);
    }

   @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> atualizarPessoa(@PathVariable Long id, @RequestBody PessoaDTO pessoaDTO) {
            PessoaDTO pessoaAtualizada = pessoaService.atualizarPessoa(id, pessoaDTO);
            return ResponseEntity.ok(pessoaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPessoa(@PathVariable Long id) {
        pessoaService.eliminarPessoa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarPessoas() {
        List<PessoaDTO> pessoas = pessoaService.listarPessoas();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/gastos")
    public ResponseEntity<List<PessoaDTO>> buscarPessoasPorNomeEPeriodo(@RequestParam String nome, @RequestParam String dataInicio, @RequestParam String dataFim) {
        List<PessoaDTO> pessoas = pessoaService.buscarPessoasPorNomeEPeriodo(nome, LocalDate.parse(dataInicio), LocalDate.parse(dataFim));
        return ResponseEntity.ok(pessoas);
 
    }
}