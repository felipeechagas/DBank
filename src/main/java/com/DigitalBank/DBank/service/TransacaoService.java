package com.DigitalBank.DBank.service;

import com.DigitalBank.DBank.interfaces.Operacoes;
import com.DigitalBank.DBank.repository.ContaRepository;
import com.DigitalBank.DBank.model.Conta;
import com.DigitalBank.DBank.model.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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

  }

//@Override
//  public void sacar(double valor, String numeroConta) {
//    Conta conta = contaRepository.findByNumero(numeroConta);
//    if (conta.isPresent()) {
//      Conta conta = optionalConta.get();
//      if (conta.getSaldo() >= valor) {
//        double novoSaldo = conta.getSaldo() - valor;
//        conta.setSaldo(novoSaldo);
//        contaRepository.save(conta);
//      } else {
//        throw new BadRequestException("Saldo insuficiente para realizar o saque.");
//      }
//    } else {
//      throw new BadRequestException("Conta não encontrada.");
//    }
//  }

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

  public void realizarTransacao(LocalDateTime dataHora) {
    // Convertendo LocalDateTime para Date
    Date dataHoraDate = Date.from(dataHora.atZone(ZoneId.systemDefault()).toInstant());

    // Criando uma nova transação e configurando a data e hora
    Transacao transacao = new Transacao();
    transacao.setDataHora(dataHoraDate);

    // Outras operações relacionadas à transação
  }

}
