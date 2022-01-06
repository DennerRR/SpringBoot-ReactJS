package com.denner.minhasfinancas.model.service;

import com.denner.minhasfinancas.exception.RegraNegocioException;
import com.denner.minhasfinancas.model.repository.UsuarioRepository;
import com.denner.minhasfinancas.service.UsuarioService;
import com.denner.minhasfinancas.service.impl.UsuarioServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("teste")
public class UsuarioServiceTest {

    UsuarioService service;
    UsuarioRepository repository;

    @Before
    public void setUp(){
        repository = Mockito.mock(UsuarioRepository.class);
        service = new UsuarioServiceImp(repository);
    }
    @Test(expected = Test.None.class)
    public void deveValidarEmail(){
        // cenário
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

        //ação/execução
        service.validarEmail("email@email.com");

    }

    @Test(expected = RegraNegocioException.class)
    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado(){
        //cenário
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        //ação
        service.validarEmail("email@email.com");
    }
}
