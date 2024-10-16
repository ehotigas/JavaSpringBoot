package com.example.demo.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.BuscarLancamentoDto;
import com.example.demo.controller.dto.CriarLancamentoDto;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.entity.Lancamento;
import com.example.demo.model.entity.Usuario;
import com.example.demo.model.enums.TipoLancamento;
import com.example.demo.model.repository.LancamentoRepository;
import com.example.demo.service.ILancamentoService;
import com.example.demo.service.IUsuarioService;
import com.example.demo.service.mapper.LancamentoMapper;

@Service
public class LancamentoService implements ILancamentoService {
    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private LancamentoMapper mapper;

    @Autowired
    private IUsuarioService usuarioService;


    @Override
    @Transactional
    public Lancamento atualizar(Long id, CriarLancamentoDto lancamento) {
        Lancamento temp = repository.findById(id).orElseThrow(() -> new NotFoundException("Lancamento with id: " + id + " not found"));
        mapper.partialUpdateLancamento(temp, lancamento);
        return repository.save(temp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamento> buscar(BuscarLancamentoDto filtros) {
        Lancamento lancamento = mapper.toEntity(filtros);
        Example<Lancamento> example = Example.of(lancamento, ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
        return repository.findAll(example);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        Lancamento lancamento = repository.findById(id).orElseThrow(() -> new NotFoundException("Lancamento with id: " + id + " not found"));
        repository.delete(lancamento);
    }

    @Override
    @Transactional
    public Lancamento salvar(CriarLancamentoDto lancamento) {
        Usuario usuario = usuarioService.findById(lancamento.getUsuario());
        LocalDate date = LocalDate.now();

        Lancamento lancamentoToSave = mapper.toEntity(lancamento);
        lancamentoToSave.setUsuario(usuario);
        lancamentoToSave.setDataCadastro(date);
        lancamentoToSave.setAno(date.getYear());
        lancamentoToSave.setMes(date.getMonthValue());
        
        return repository.save(lancamentoToSave);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal obterSaldo(Long usuarioId) {
        BigDecimal receita = repository.obterSaldoPorTipoLancamentoEUsuario(usuarioId, TipoLancamento.RECEITA);
        BigDecimal despesa = repository.obterSaldoPorTipoLancamentoEUsuario(usuarioId, TipoLancamento.DESPESA);
        if (receita == null) {
            receita = BigDecimal.ZERO;
        }
        if (despesa == null) {
            despesa = BigDecimal.ZERO;
        }
        return receita.subtract(despesa);
    }
}
