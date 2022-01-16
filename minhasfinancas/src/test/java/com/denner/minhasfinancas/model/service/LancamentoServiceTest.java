package com.denner.minhasfinancas.model.service;

import com.denner.minhasfinancas.exception.RegraNegocioException;
import com.denner.minhasfinancas.model.entity.Lancamento;
import com.denner.minhasfinancas.model.enums.StatusLancamento;
import com.denner.minhasfinancas.model.repository.LancamentoRepository;
import com.denner.minhasfinancas.model.repository.LancamentoRepositoryTest;
import com.denner.minhasfinancas.service.impl.LancamentoServiceImp;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @SpyBean
    LancamentoServiceImp service;
    @MockBean
    LancamentoRepository repository;

    @Test
    public void deveSalvarUmLancamento(){
        Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarLancamento();
        Mockito.doNothing().when(service).validar(lancamentoASalvar);

        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(1l);
        Mockito.when(repository.save(lancamentoASalvar)).thenReturn(lancamentoSalvo);

        Lancamento lancamento = service.salvar(lancamentoASalvar);

        // verificação
        Assertions.assertThat(lancamento.getId()).isEqualTo(lancamentoSalvo.getId());
        Assertions.assertThat(lancamento.getStatus()).isEqualTo(StatusLancamento.PENDENTE);
    }
    @Test
    public void naoDeveSalvarUmLancamentoQuandoHouverErroDeValidacao(){
        Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarLancamento();
        Mockito.doThrow(RegraNegocioException.class).when(service).validar(lancamentoASalvar);

        Assertions.catchThrowableOfType(() ->service.salvar(lancamentoASalvar),RegraNegocioException.class);

        Mockito.verify(repository,Mockito.never()).save(lancamentoASalvar);
    }
}
