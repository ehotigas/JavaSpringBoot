package com.example.demo.model.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.entity.Lancamento;
import com.example.demo.model.enums.TipoLancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    @Query(value = "select sum(A.Valor) from Lancamento A left join A.usuario B where B.id = :idUsuario and A.tipo = :tipo group by U")
    BigDecimal obterSaldoPorTipoLancamentoEUsuario(@Param("idUsuario") Long idUsuario, @Param("tipo") TipoLancamento tipo);
}
