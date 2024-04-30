package com.DigitalBank.DBank.repository;

import com.DigitalBank.DBank.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCpf(String cpf);

    Optional<Cliente> findByCpf(String cpf);

    @Query("SELECT DISTINCT c FROM Cliente c LEFT JOIN FETCH c.cartaoCredito LEFT JOIN FETCH c.conta")
    List<Cliente> findAllClientesWithCartaoCreditoAndConta();
}
