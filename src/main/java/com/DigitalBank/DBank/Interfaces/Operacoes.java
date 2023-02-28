package com.DigitalBank.DBank.Interfaces;

import com.DigitalBank.DBank.model.Conta;

import java.text.ParseException;

/** Esta ‘interface’ representa um contrato para operações bancárias.
 *
 * Uma operação deve ser saque, depósito e transferência <p><br />
 *
 * @author FelipeChagas
 * @since 28/02/2023
 * @version 1.0
 */

public interface Operacoes {

  /** Esta função é responsável por sacar dinheiro de uma conta.
   *
   * @author FelipeChagas
   * @since 28/02/2023
   * @version 1.0
   */
  public void sacar(Conta conta, double valor);

  /** Esta função é responsável por inserir dinheiro em uma conta.
   *
   * @author FelipeChagas
   * @since 28/02/2023
   * @version 1.0
   */
  public void depositar(Conta conta, double valor);

  /** Esta função é responsável por transferir dinheiro entre contas bancárias.
   *
   * @author FelipeChagas
   * @since 28/02/2023
   * @version 1.0
   */
  public void transferir(Conta suaConta, Conta contaDestino, double valor);

  /** Esta função é responsável por solicitar cartões de crédito.
   *
   * @author FelipeChagas
   * @since 28/02/2023
   * @version 1.0
   */
  public void solicitarCartao(Conta conta) throws ParseException;

  /** Esta função é responsável por exibir os dados bancários de uma conta.
   *
   * @author FelipeChagas
   * @since 28/02/2023
   * @version 1.0
   */
  public void exibirDadosBancarios(Conta conta);
}
