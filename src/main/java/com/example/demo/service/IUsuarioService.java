package com.example.demo.service;

import com.example.demo.model.entity.Usuario;

public interface IUsuarioService {
    Usuario autenticar(String email, String senha);

    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);
}
