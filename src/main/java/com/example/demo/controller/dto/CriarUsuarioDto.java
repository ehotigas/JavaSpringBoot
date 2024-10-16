package com.example.demo.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CriarUsuarioDto {
    private String email;
    private String nome;
    private String senha;
}
