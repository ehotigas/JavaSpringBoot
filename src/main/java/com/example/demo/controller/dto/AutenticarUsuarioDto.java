package com.example.demo.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AutenticarUsuarioDto {
    private String email;
    private String senha;
}
