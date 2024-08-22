package com.crja.tasks_mngr_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crja.tasks_mngr_api.models.Pessoa;
import com.crja.tasks_mngr_api.repository.PessoaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa salvarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa atualizarPessoa(Long id, Pessoa pessoa) {
        Optional<Pessoa> existente = pessoaRepository.findById(id);
        if (existente.isPresent()) {
            Pessoa pessoaAtualizada = existente.get();
            pessoaAtualizada.setNome(pessoa.getNome());
            pessoaAtualizada.setDepartamento(pessoa.getDepartamento());
            return pessoaRepository.save(pessoaAtualizada);
        } else {
            throw new RuntimeException("Pessoa não encontrada");
        }
    }

    public void removerPessoa(Long id) {
        pessoaRepository.deleteById(id);
    }

    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }

    public List<Pessoa> buscarPorNome(String nome) {
        return pessoaRepository.findByNomeContaining(nome);
    }
}

