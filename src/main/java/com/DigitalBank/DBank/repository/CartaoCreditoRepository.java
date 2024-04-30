package com.DigitalBank.DBank.repository;

import com.DigitalBank.DBank.model.CartaoCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Integer> {

    Optional<CartaoCredito> findByNumeroCartao(String numeroCartao);
}
