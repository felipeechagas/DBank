package com.DigitalBank.DBank.Service;

import com.DigitalBank.DBank.Interfaces.Operacoes;
import com.DigitalBank.DBank.Repository.ContaRepository;
import com.DigitalBank.DBank.exception.BadRequestException;
import com.DigitalBank.DBank.model.Conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

/** Esta é a entidade que executa as transações no sistema.
 *
 * Uma transação é definada na ‘interface’ de {@link Operacoes}. <p><br />
 *
 * @author FelipeChagas
 * @since 28/02/2023
 * @version 1.0
 */

@Service
public class TransacaoService implements Operacoes {

  @Autowired
  private ContaRepository contaRepository;

  @Override
  public void sacar(double valor, String numeroConta) {
    Optional<Conta> optionalConta = contaRepository.findByNumero(numeroConta);
    if (optionalConta.isPresent()) {
      Conta conta = optionalConta.get();
      if (conta.getSaldo() >= valor) {
        double novoSaldo = conta.getSaldo() - valor;
        conta.setSaldo(novoSaldo);
        contaRepository.save(conta);
      } else {
        throw new BadRequestException("Saldo insuficiente para realizar o saque.");
      }
    } else {
      throw new BadRequestException("Conta não encontrada.");
    }
  }

  @Override
  public void depositar(Conta conta, double valor) {

  }

  @Override
  public void transferir(Conta suaConta, Conta contaDestino, double valor) {

  }

  @Override
  public void solicitarCartao(Conta conta) throws ParseException {

  }

  @Override
  public void exibirDadosBancarios(Conta conta) {

  }

}
