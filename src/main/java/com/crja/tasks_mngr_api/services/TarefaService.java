package com.crja.tasks_mngr_api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crja.tasks_mngr_api.models.Pessoa;
import com.crja.tasks_mngr_api.models.Tarefa;
import com.crja.tasks_mngr_api.repository.PessoaRepository;
import com.crja.tasks_mngr_api.repository.TarefaRepository;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Tarefa salvarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public Tarefa alocarPessoaNaTarefa(Long tarefaId, Long pessoaId) {
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(tarefaId);
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(pessoaId);
        
        if (tarefaOptional.isPresent() && pessoaOptional.isPresent()) {
            Tarefa tarefa = tarefaOptional.get();
            Pessoa pessoa = pessoaOptional.get();
            
            if (tarefa.getDepartamento().equals(pessoa.getDepartamento())) {
                tarefa.setPessoaAlocada(pessoa);
                return tarefaRepository.save(tarefa);
            } else {
                throw new RuntimeException("Departamento incompatível");
            }
        } else {
            throw new RuntimeException("Tarefa ou Pessoa não encontrada");
        }
    }

    public Tarefa finalizarTarefa(Long id) {
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
        if (tarefaOptional.isPresent()) {
            Tarefa tarefa = tarefaOptional.get();
            tarefa.setFinalizado(true);
            return tarefaRepository.save(tarefa);
        } else {
            throw new RuntimeException("Tarefa não encontrada");
        }
    }

    public List<Tarefa> listarTarefasPendentes() {
        return tarefaRepository.findTop3ByPessoaAlocadaIsNullOrderByPrazoAsc();
    }
}
