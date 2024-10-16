package com.example.demo.controller.dto;

import java.math.BigDecimal;

import com.example.demo.model.enums.StatusLancamento;
import com.example.demo.model.enums.TipoLancamento;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CriarLancamentoDto {
    private String descricao;
    private Long usuario;
    private BigDecimal valor;
    private TipoLancamento tipo;
    private StatusLancamento status;
}
