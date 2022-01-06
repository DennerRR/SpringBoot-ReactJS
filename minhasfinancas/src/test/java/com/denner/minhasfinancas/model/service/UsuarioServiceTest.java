package com.denner.minhasfinancas.model.service;

import com.denner.minhasfinancas.exception.ErroAutenticacao;
import com.denner.minhasfinancas.exception.RegraNegocioException;
import com.denner.minhasfinancas.model.entity.Usuario;
import com.denner.minhasfinancas.model.repository.UsuarioRepository;
import com.denner.minhasfinancas.service.UsuarioService;
import com.denner.minhasfinancas.service.impl.UsuarioServiceImp;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("teste")
public class UsuarioServiceTest {

    @SpyBean
    UsuarioServiceImp service;

    @MockBean
    UsuarioRepository repository;


    @Test(expected = Test.None.class)
    public void deveSalvarUmUsuario(){
        // cenário
        Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
        Usuario usuario = Usuario.builder().id(1l).nome("nome").email("email@email.com").senha("senha").build();

        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        // acao
        Usuario usuarioSalvo = service.salvarUsuario(new Usuario());

        //verificaçao
        Assertions.assertThat(usuarioSalvo).isNotNull();
        Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
        Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
        Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
        Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
    }

    @Test(expected = Test.None.class)
    public void deveAutenticarUmUsuarioComSucesso(){
        // cenário
        String email = "email@email.com";
        String senha = "senha";

        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

        // acao

        Usuario result = service.autenticar(email, senha);

        // verificação
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComEmailInformado(){
        // cenário
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        // acao
        Throwable exception = Assertions.catchThrowable(()->service.autenticar("email@email.com", "senha"));

        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Usuário não encontrado para o email informado.");
    }
    @Test
    public void deveLancarErroQuandoSenhaNaoBater(){
        // cenário
        String senha = "senha";
        Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        // ação


        Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com","123"));
        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida.");

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
