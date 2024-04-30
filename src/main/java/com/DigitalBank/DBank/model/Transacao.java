package com.DigitalBank.DBank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    @Column(name = "tipo_transacao")
    private String tipoTransacao;

    private Date data;

    private double valor;

    @ManyToOne
    @JoinColumn(name = "conta_origem_id")
    private Conta contaOrigem;

    @ManyToOne
    @JoinColumn(name = "conta_destino_id")
    private Conta contaDestino;

    @Column(name = "data_hora")
    private Date dataHora;

    public void setDataHora(Date now) {
    }

    public void setDataHora(LocalDateTime now) {
    }
}
