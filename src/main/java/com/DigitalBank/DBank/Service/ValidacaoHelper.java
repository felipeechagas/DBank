package com.DigitalBank.DBank.Service;

import com.DigitalBank.DBank.model.Conta;
import org.springframework.stereotype.Service;

import java.util.List;

/** Esta entendidade valida os dados da {@link Conta}.
 *
 * @author FelipeChagas
 * @since 28/02/2023
 * @version 1.0
 */
@Service
public class ValidacaoHelper {

  public static boolean isPossivelCadastrarConta(Conta conta) {
    if(!verificarDadosPessoa(conta)) {
      System.out.println("Não foi possível cadastrar a conta!");
      return false;
    }

    return true;
  }

  public static boolean verificarDadosPessoa(Conta conta) {
    if(conta == null) {
      System.out.println("Conta não foi preenchida.");

      return false;
    }

    if(conta.getCliente().getNome().isEmpty()) {
      System.out.println("Nome não foi preenchido.");

      return false;
    }

    if(conta.getCliente().getCpf().isEmpty()) {
      System.out.println("CPF não foi preenchido.");

      return false;
    }

    if(conta.getCliente().getGenero().isEmpty()) {
      System.out.println("Gênero não foi preenchido.");

      return false;
    }

    if(conta.getCliente().getDataNascimento() == null) {
      System.out.println("Data de Nascimento não foi preenchido.");

      return false;
    }

    if(conta.getNumero().isEmpty()) {
      System.out.println("Nº da Conta não foi preenchido.");

      return false;
    }

    return true;
  }

  public static boolean isPossivelRealizarOperacaoBancaria(Conta conta, double valor) {
    if(valor <= conta.getSaldo()) {
      return true;
    }else{
      System.out.println("Não foi possível realizar a operação, verifique o valor informado!");

      return false;
    }
  }

  public static boolean isPossivelRealizarDeposito(double valor) {
    if(valor > 0) {
      return true;
    } else {
      System.out.println("Não foi possível realizar o depósito, verifique o valor informado!");

      return false;
    }
  }

  public static boolean isPossivelSolicitarCartao(Conta conta) {
    if(conta.getSaldo() > 50) {
      return true;
    }else {
      System.out.println("Não é possível solicitar um cartão! Verifique o seu tempo de conta.");

      return false;
    }
  }

  public static boolean isContaExistente(List<Conta> contas, String cpf) {
    if(!contas.isEmpty()) {
      for (Conta conta : contas) {
        if(conta.getCliente().getCpf().equals(cpf)) {
          return true;
        }
      }
    }

    System.out.println("O CPF informado não pertence a nenhuma conta!");

    return false;
  }
}
