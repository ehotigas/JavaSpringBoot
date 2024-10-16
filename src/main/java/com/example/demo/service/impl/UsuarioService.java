package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exceptions.EmailAlreadyExistsException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.exceptions.UnAuthorizedException;
import com.example.demo.model.entity.Usuario;
import com.example.demo.model.repository.UsuarioRepository;
import com.example.demo.service.IUsuarioService;


@Service
public class UsuarioService implements IUsuarioService {
    @Autowired
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario findById(Long id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));
        return usuario;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Usuario usuario = repository.findByEmail(email).orElseThrow(
            () -> new UnAuthorizedException("Usuario não encontrado")
        );

        if (!usuario.getSenha().equals(senha)) {
            throw new UnAuthorizedException("Senha Inválida");
        }
        
        return usuario;
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = this.repository.existsByEmail(email);
        if (existe) {
            throw new EmailAlreadyExistsException("Email já cadastrado no sistema.");
        }
    }
}