package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.dto.BuscarLancamentoDto;
import com.example.demo.controller.dto.CriarLancamentoDto;
import com.example.demo.model.entity.Lancamento;
import com.example.demo.service.ILancamentoService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {
    @Autowired
    private ILancamentoService service;

    @GetMapping
    public ResponseEntity<List<Lancamento>> buscar(
        @RequestParam(value = "descricao", required = false) String descricao,
        @RequestParam(value = "mes", required = false) Integer mes,
        @RequestParam(value = "ano", required = false) Integer ano,
        @RequestParam(value = "usuario", required = false) Long usuarioId
    ) {
        BuscarLancamentoDto lancamentoFiltro = BuscarLancamentoDto.builder().descricao(descricao).mes(mes).ano(ano).usuario(null).build();
        return ResponseEntity.ok().body(service.buscar(lancamentoFiltro));
    }

    @PostMapping
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created", content = @Content( schema = @Schema( implementation = Lancamento.class ) ))
    })
    public ResponseEntity<Lancamento> salvar(@Valid @RequestBody CriarLancamentoDto input) {
        Lancamento lancamento = service.salvar(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamento);
    }

    @PatchMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok", content = @Content( schema = @Schema( implementation = Lancamento.class ) ))
    })
    public ResponseEntity<Lancamento> atualizar(@PathVariable("id") Long id, @Valid @RequestBody CriarLancamentoDto input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.atualizar(id, input));
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content")
    })
    public ResponseEntity<Lancamento> deletar(@PathVariable("id") Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
