package com.DigitalBank.DBank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Esta model representa uma Pessoa.
 * <p>
 * Uma pessoa possui CPF único. <p><br />
 * Uma pessoa também faz um relacionamento com {@link Genero}. <p><br />
 *
 * @author FelipeChagas
 * @version 1.0
 * @since 28/02/2023
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_cliente")
public class Cliente {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nome;
  private Date dataNascimento;
  private String cpf;
  private String genero;
  private String contato;


  @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
  @JsonManagedReference
  private CartaoCredito cartaoCredito;

  @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
  @JsonManagedReference
  private Conta conta;


}
