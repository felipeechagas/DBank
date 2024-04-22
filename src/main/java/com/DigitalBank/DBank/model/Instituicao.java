package com.DigitalBank.DBank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Esta model representa uma Instituição Bancária.
 * <p>
 * Uma instituição possui CNPJ único. <p><br />
 * Uma instituição é regulamentada pelo Governo Federal. <p><br />
 *
 * @author FelipeChagas
 * @version 1.0
 * @see <a href="https://pt.wikipedia.org/wiki/Brasil">Site do Brasil</>
 * @since 28/02/2023
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "tb_instituicao")
public class Instituicao {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nome;
  private String cnpj;
  private Date dataCriacao;
  private String codigo;

  public Instituicao() {
    this.nome = "DBank";
    this.cnpj = "79773685000169";
    this.dataCriacao = new Date();
    this.codigo = "0001";
  }

}
