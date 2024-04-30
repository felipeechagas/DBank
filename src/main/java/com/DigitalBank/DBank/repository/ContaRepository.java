package com.DigitalBank.DBank.repository;

import com.DigitalBank.DBank.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    boolean existsByNumero(String numero);

    Conta findByNumero(String numeroConta);
}
