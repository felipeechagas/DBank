package com.DigitalBank.DBank.model;

import java.util.Date;

/** Esta model representa uma Instituição Bancária.
 *
 * Uma instituição possui CNPJ único. <p><br />
 * Uma instituição é regulamentada pelo Governo Federal. <p><br />
 *
 * @author FelipeChagas
 * @since 28/02/2023
 * @version 1.0
 * @see <a href="https://pt.wikipedia.org/wiki/Brasil">Site do Brasil</>
 */

public class Instituicao {

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

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  public Date getDataCriacao() {
    return dataCriacao;
  }

  public void setDataCriacao(Date dataCriacao) {
    this.dataCriacao = dataCriacao;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }
}
