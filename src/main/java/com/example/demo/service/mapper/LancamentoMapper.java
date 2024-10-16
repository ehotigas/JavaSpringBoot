package com.example.demo.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.controller.dto.BuscarLancamentoDto;
import com.example.demo.controller.dto.CriarLancamentoDto;
import com.example.demo.model.entity.Lancamento;
import com.example.demo.model.entity.Usuario;
import com.example.demo.service.impl.UsuarioService;

@Mapper(componentModel = "spring")
public abstract class LancamentoMapper {
    @Autowired
    private UsuarioService usuarioService;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "ano", ignore = true)
    @Mapping(target = "mes", ignore = true)
    public abstract void partialUpdateLancamento(@MappingTarget Lancamento lancamento, CriarLancamentoDto updateLancamento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "ano", ignore = true)
    @Mapping(target = "mes", ignore = true)
    public abstract Lancamento toEntity(CriarLancamentoDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "tipo", ignore = true)
    @Mapping(target = "valor", ignore = true)
    public abstract Lancamento toEntity(BuscarLancamentoDto dto);

    protected Usuario map(Long usuarioId) {
        return usuarioId != null ? usuarioService.findById(usuarioId) : null;
    }
}
