package com.example.demo.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.model.entity.Usuario;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {
    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager;

    private Usuario criarUsuario(String email) {
        Usuario usuario = Usuario.builder().nome("test").email(email).build();
        return entityManager.persist(usuario);
    }
    
    @Test
    public void deveVerificarAExistenciaDeUmEmail() {
        String email = "exemple@email.com";
        criarUsuario(email);

        boolean result = this.repository.existsByEmail(email);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail() {
        String email = "exemple@email.com";

        boolean result = this.repository.existsByEmail(email);
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void devePersistirUmUsuarioNaBaseDeDados() {
        Usuario usuario = Usuario.builder().build();

        Usuario result = repository.save(usuario);

        Assertions.assertThat(result.getId()).isNotNull();
    }

    @Test
    public void deveEncontrarUmUsuarioPorEmail() {
        String email = "exemple@email.com";
        criarUsuario(email);
        
        Optional<Usuario> result = repository.findByEmail(email);
        Assertions.assertThat(result.isPresent()).isTrue();
    }

    
    @Test
    public void deveRetornarVazioAoBuscarUmUsuarioPorEmailQueNaoExisteNaBase() {
        String email = "exemple@email.com";
        Optional<Usuario> result = repository.findByEmail(email);
        Assertions.assertThat(result.isPresent()).isFalse();
    }
}
