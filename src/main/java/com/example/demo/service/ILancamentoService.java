package com.example.demo.service;

import java.util.List;

import com.example.demo.controller.dto.BuscarLancamentoDto;
import com.example.demo.controller.dto.CriarLancamentoDto;
import com.example.demo.model.entity.Lancamento;

public interface ILancamentoService {
    public List<Lancamento> buscar(BuscarLancamentoDto filtros);

    public Lancamento salvar(CriarLancamentoDto lancamento);

    public Lancamento atualizar(Long id, CriarLancamentoDto lancamento);

    public void deletar(Long id);
}
