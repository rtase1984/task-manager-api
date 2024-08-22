package com.crja.tasks_mngr_api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.crja.tasks_mngr_api.models.Pessoa;
import com.crja.tasks_mngr_api.repository.PessoaRepository;

@SpringBootTest
public class PessoaServiceTest {
     @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    public PessoaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvarPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João");
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        Pessoa resultado = pessoaService.salvarPessoa(pessoa);

        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
        verify(pessoaRepository, times(1)).save(pessoa);
    }
    
}