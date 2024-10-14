package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.EmailAlreadyExistsException;
import com.example.demo.model.entity.Usuario;
import com.example.demo.model.repository.UsuarioRepository;
import com.example.demo.service.IUsuarioService;


@Service
public class UsuarioService implements IUsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Override
    public Usuario autenticar(String email, String senha) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = this.repository.existsByEmail(email);
        if (existe) {
            throw new EmailAlreadyExistsException("Email já cadastrado no sistema.");
        }
    }
}