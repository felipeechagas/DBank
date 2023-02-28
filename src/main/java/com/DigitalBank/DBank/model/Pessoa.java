package com.DigitalBank.DBank.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/** Esta model representa uma Pessoa.
 *
 * Uma pessoa possui CPF único. <p><br />
 * Uma pessoa também faz um relacionamento com {@link Genero}. <p><br />
 *
 * @author FelipeChagas
 * @since 28/02/2023
 * @version 1.0
 */

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "tb_pessoa")
public class Pessoa {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String nome;
  private Date dataNascimento;
  private String cpf;
  private Genero genero;
}
