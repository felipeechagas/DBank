package com.DigitalBank.DBank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;

/**
 * Esta model representa uma Conta.
 * <p>
 * Uma conta deve ser ligada apenas uma {@link Cliente}. <p><br />
 * Quem regulamenta a conta é a {@link Instituicao}. <p><br />
 *
 * @author FelipeChagas
 * @version 1.0
 * @since 28/01/2023
 */

@Entity
@Data
@AllArgsConstructor
@Table(name = "tb_conta")
public class Conta extends Instituicao {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String numero;
  private boolean ativa;
  private Double saldo;
  private String tipoConta;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  @Temporal(TemporalType.TIMESTAMP)
  private Date dataCriacao;

  @OneToOne
  @JoinColumn(name = "cartaoCredito_numeroCartao")
  private CartaoCredito cartaoCredito;

  public Conta() {
    Random random = new Random();
    this.numero = String.format("%04d-%03d", random.nextInt(10000), random.nextInt(1000));
    this.cliente = new Cliente();
    this.saldo = 0.0;
    this.ativa = Boolean.TRUE;
  }

  public void setAtiva(boolean ativa) {
    this.ativa = ativa;
  }

}
