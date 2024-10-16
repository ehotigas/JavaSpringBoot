package com.example.demo.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BuscarLancamentoDto {
    private String descricao;
    private Long usuario;
    private Integer ano;
    private Integer mes;
}
