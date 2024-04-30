package com.DigitalBank.DBank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_transacaoDetalhada")
public class TransacaoDetalhada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoTransacao;
    private LocalDateTime dataHora;
    private double valor;
    private String nomeBanco;
    private String nomeCliente;
    private String numeroConta;
}
