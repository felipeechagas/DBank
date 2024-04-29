package com.DigitalBank.DBank.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "tb_genero")
public class Genero {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  public static final char MASCULINO = 'M';
  public static final char FEMININO = 'F';
  private Character genero;

  public static String getNomeGenero(char genero) {
    switch (genero) {
      case MASCULINO:
        return "Masculino";
      case FEMININO:
        return "Feminino";
      default:
        return "Desconhecido";
    }
  }

  public Genero(char genero) {
    this.genero = genero;
  }

  public static Genero modificarStringToGenero(String genero) {
    switch (genero) {
      case "Masculino":
        return new Genero(MASCULINO);
      case "Feminino":
        return new Genero(FEMININO);
      default:
        return new Genero();
    }
  }

  public boolean isGeneroMasculino(Character genero) {
    return genero != null && genero == MASCULINO;
  }

  public boolean isGeneroFeminino(Character genero) {
    return genero != null && genero == FEMININO;
  }

  public boolean isEmpty() {
    return this.genero == null;
  }

}
