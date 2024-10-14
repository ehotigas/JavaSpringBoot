package com.example.demo.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.model.entity.Usuario;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UsuarioRepositoryTest {
    @Autowired
    UsuarioRepository repository;
    
    @Test
    public void deveVerificarAExistenciaDeUmEmail() {
        String email = "exemple@email.com";
        Usuario usuario = Usuario.builder().nome("test").email(email).build();
        this.repository.save(usuario);

        boolean result = this.repository.existsByEmail(email);

        Assertions.assertThat(result).isTrue();
    }
}
