package com.DigitalBank.DBank.repository;

import com.DigitalBank.DBank.model.TransacaoDetalhada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransacaoDetalhadaRepository extends JpaRepository<TransacaoDetalhada, Long> {

       List<TransacaoDetalhada> findByNumeroContaAndDataHoraBetween(String numeroConta, LocalDateTime dataInicial, LocalDateTime dataFinal);
}
