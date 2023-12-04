package com.DigitalBank.DBank.Repository;

import com.DigitalBank.DBank.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    boolean existsByNumero(String numero);
}
