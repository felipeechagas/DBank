package com.DigitalBank.DBank.repository;

import com.DigitalBank.DBank.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

       List<Transacao> findByContaNumeroAndDataBetween(String numeroConta, LocalDate dataInicial, LocalDate dataFinal);
}
