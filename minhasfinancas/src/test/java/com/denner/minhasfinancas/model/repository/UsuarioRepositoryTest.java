package com.denner.minhasfinancas.model.repository;

import com.denner.minhasfinancas.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveVerificarAExistenciaDeUmEmail(){

        //cenário
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        //ação/ execução
        boolean result = repository.existsByEmail("usuario@email.com");


        // verificação
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail(){

        //ação / execução
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificação
        Assertions.assertThat(result).isFalse();
    }
    @Test
    public void devePersistirUmUsuarioNaBaseDeDados(){
        //cenário
        Usuario usuario = criarUsuario();
        // ação
        Usuario usuarioSalvo = repository.save(usuario);
        //verificação
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();

    }

    @Test
    public void deveBuscarUmUsuarioPorEmail(){
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        Optional<Usuario> result = repository.findByEmail("usuario@email.com");

        Assertions.assertThat(result.isPresent()).isTrue();

    }

    @Test
    public void deveRetornarVazioAoBuscarUmUsuarioPorEmailQuandoNaoExisteNaBase(){

        Optional<Usuario> result = repository.findByEmail("usuario@email.com");

        Assertions.assertThat(result.isPresent()).isFalse();

    }
    public static Usuario criarUsuario(){
        return Usuario.builder().nome("usuario").email("usuario@email.com").senha("senha").build();
    }
}
