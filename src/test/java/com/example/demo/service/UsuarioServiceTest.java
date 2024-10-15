package com.example.demo.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.exceptions.EmailAlreadyExistsException;
import com.example.demo.exceptions.UnAuthorizedException;
import com.example.demo.model.entity.Usuario;
import com.example.demo.model.repository.UsuarioRepository;
import com.example.demo.service.impl.UsuarioService;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
    @SpyBean
    private UsuarioService service;

    @MockBean
    private UsuarioRepository repository;

    @BeforeEach
    public void setUp() {
        // service = Mockito.spy(UsuarioService.class);
    }

    @Test
    public void deveValidarEmail() {
        String email = "test@email.com";
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
        
        this.service.validarEmail(email);
        Mockito.verify(repository, Mockito.times(1)).existsByEmail(email);
    }

    @Test
    public void deveLancarErroQuandoExistirEmailCadastrado() {
        String email = "test@email.com";
        Usuario.builder().email(email).build();
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
        
        Assertions.assertThatThrownBy(() -> {
            this.service.validarEmail(email);
        }).isInstanceOf(EmailAlreadyExistsException.class);
        Mockito.verify(repository, Mockito.times(1)).existsByEmail(email);
    }

    @Test
    public void deveAutenticarUmUsuarioComSucesso() {
        String email = "test@email.com", senha = "test";
        Usuario user = Usuario.builder().email(email).senha(senha).build();
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        Usuario result = service.autenticar(email, senha);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(user);
        Mockito.verify(repository, Mockito.times(1)).findByEmail(email);
    }

    @Test
    public void deveLancarUmaExecaoDeUsuarioNaoEncontrado() {
        String email = "test@email.com", senha = "test";
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            service.autenticar(email, senha);
        }).isInstanceOf(UnAuthorizedException.class).hasMessage(
            "Usuario não encontrado"
        );
    }

    @Test
    public void deveLancarUmaExcessaoAoVerificarSenhaIncorreta() {
        String email = "test@email.com", senha = "test";
        Usuario usuario = Usuario.builder().email(email).senha("123").build();
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        Assertions.assertThatThrownBy(() -> {
            service.autenticar(email, senha);    
        }).isInstanceOf(UnAuthorizedException.class).hasMessage(
            "Senha Inválida"
        );
    }

    @Test
    public void deveSalvarUmUsuario() {
        Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
        String email = "test@email.com";
        Usuario usuario = Usuario.builder().email(email).senha("123").build();
        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        Usuario result = service.salvarUsuario(usuario);
        Assertions.assertThat(result).isEqualTo(usuario);
        Mockito.verify(repository, Mockito.times(1)).save(usuario);
    }

    @Test
    public void deveLancarUmaExcessaoAoSalvarUsuario() {
        Mockito.doThrow(EmailAlreadyExistsException.class).when(service).validarEmail(Mockito.anyString());
        String email = "test@email.com";
        Usuario usuario = Usuario.builder().email(email).senha("123").build();
        
        Assertions.assertThatThrownBy(() -> {
            service.salvarUsuario(usuario);
        }).isInstanceOf(EmailAlreadyExistsException.class);
        Mockito.verify(repository, Mockito.never()).save(usuario);
    }
}
