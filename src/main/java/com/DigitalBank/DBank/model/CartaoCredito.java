package com.DigitalBank.DBank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

/**
 * Este model define cartão de crédito.
 * <p>
 * Um cartão de crédito é composto por: <p><br />
 * Um número de 16 dígitos <p><br />
 * Data de validade do cartão <p><br />
 * Código de segurança de 3 dígitos <p><br />
 *
 * @author FelipeChagas
 * @version 1.0
 * @since 28/02/2023
 */

@Entity
@Data
@AllArgsConstructor
@Table(name = "tb_cartaoCredito")
public class CartaoCredito {

  private static final long serialVersionUID = 1L;

  @Id
  private String numeroCartao;

  public enum Status {
    ATIVO, BLOQUEADO, CANCELADO
  }

  @Enumerated(EnumType.STRING)
  private Status status;

  private String validade; //deve ser mes/ano
  private Integer codigoSeguranca;
  private String vencimentoFatura; //apenas o dia

  @OneToOne
  @JoinColumn(name = "cliente_id")
  @JsonBackReference
  private Cliente cliente;

  private double limiteCredito;
  private double limiteCreditoUtilizado;

  public CartaoCredito() throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

    Random random = new Random();

    this.numeroCartao = generateRandomNumber(16);
    this.codigoSeguranca = random.nextInt(3);
    this.limiteCredito = 300.0;
  }

  private String generateRandomNumber(int length) {
    Random random = new Random();
    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < length; i++) {
      builder.append(random.nextInt(10)); // Append a random digit
    }

    return builder.toString();
  }

  private String calcularAnoValidade() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.YEAR, 3); // Adiciona 3 anos à data atual
    int ano = calendar.get(Calendar.YEAR);
    return String.valueOf(ano);
  }

  public void gerarCodigoSeguranca() {
    Random random = new Random();
    int codigoAleatorio = random.nextInt(900) + 100;
    this.codigoSeguranca = codigoAleatorio;
  }

  private int calcularDiferencaDias(Date dataInicio, Date dataFim) {
    long diffEmMillis = Math.abs(dataFim.getTime() - dataInicio.getTime());
    return (int) (diffEmMillis / (24 * 60 * 60 * 1000));
  }

}
