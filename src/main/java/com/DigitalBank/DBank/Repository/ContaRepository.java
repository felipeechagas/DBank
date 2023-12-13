package com.DigitalBank.DBank.Repository;

import com.DigitalBank.DBank.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    boolean existsByNumero(String numero);

    Optional<Conta> findByNumero(String numeroConta);
}
