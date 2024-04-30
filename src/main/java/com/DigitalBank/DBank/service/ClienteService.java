package com.DigitalBank.DBank.service;

import com.DigitalBank.DBank.repository.CartaoCreditoRepository;
import com.DigitalBank.DBank.repository.ClienteRepository;
import com.DigitalBank.DBank.exception.BadRequestException;
import com.DigitalBank.DBank.model.CartaoCredito;
import com.DigitalBank.DBank.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ClienteService {

  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private CartaoCreditoRepository cartaoCreditoRepository;

  public Cliente findByIdOrThrowBadRequestException(long id) {
    return clienteRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("Cliente não encontrado"));
  }

  public List<Cliente> listarClientes(){
    return clienteRepository.findAll();
  }

  public Cliente save(Cliente cliente) {
    try {
      if (clienteRepository.existsByCpf(cliente.getCpf())) {
        throw new BadRequestException("Cliente com CPF " + cliente.getCpf() + " já existe.");
      }
      return clienteRepository.save(cliente);
    } catch (BadRequestException e) {
      throw e;
    } catch (Exception e) {
      System.err.println("Erro ao salvar cliente: " + e.getMessage());
      throw new RuntimeException("Erro interno ao salvar cliente.");
    }
  }

  public CartaoCredito criarCartaoCreditoParaClienteExistente(CartaoCredito cartaoCredito) {
    Cliente clienteAssociado = cartaoCredito.getCliente();

    // Verifica se o cliente associado já possui um ID
    if (clienteAssociado.getId() != null) {
      Optional<Cliente> optionalCliente = clienteRepository.findById(clienteAssociado.getId());
      if (optionalCliente.isPresent()) {
        Cliente clienteExistente = optionalCliente.get();
        if (clienteExistente.getCartaoCredito() == null) {
          cartaoCredito.setCliente(clienteExistente);

          // Gera um número de cartão automaticamente
          Random random = new Random();
          String numeroCartao = String.format("%04d-%04d-%04d-%04d",
                  random.nextInt(10000), random.nextInt(10000),
                  random.nextInt(10000), random.nextInt(10000));
          cartaoCredito.setNumeroCartao(numeroCartao);

          // Gera um código de segurança (CVV) de três dígitos aleatório
          String codigoSeguranca = String.format("%03d", random.nextInt(1000));
          cartaoCredito.setCodigoSeguranca(Integer.valueOf(codigoSeguranca));

          // Define a data de validade do cartão (3 anos a partir da data atual)
          Calendar calendarValidade = Calendar.getInstance();
          calendarValidade.add(Calendar.YEAR, 3);
          Date validadeCartao = calendarValidade.getTime();
          cartaoCredito.setValidade(new SimpleDateFormat("MM/yyyy").format(validadeCartao));

          // Define o status do cartão como ativo
          cartaoCredito.setStatus(CartaoCredito.Status.ATIVO);

          // Salva o cartão de crédito no banco de dados
          return cartaoCreditoRepository.save(cartaoCredito);
        } else {
          throw new BadRequestException("Cliente já possui um cartão de crédito.");
        }
      } else {
        throw new BadRequestException("Cliente não encontrado. Não é possível criar o cartão de crédito.");
      }
    } else {
      throw new BadRequestException("ID do cliente não fornecido. Não é possível criar o cartão de crédito.");
    }
  }



  public Cliente updateCliente(Long id, Cliente clienteAtualizado) {
    Cliente clienteExistente = clienteRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("Cliente não encontrado com o ID: " + id));

      clienteExistente.setNome(clienteAtualizado.getNome());
      clienteExistente.setCpf(clienteAtualizado.getCpf());
      clienteExistente.setGenero(clienteAtualizado.getGenero());
      clienteExistente.setDataNascimento(clienteAtualizado.getDataNascimento());
      clienteExistente.setContato(clienteAtualizado.getContato());

      return clienteRepository.save(clienteExistente);
  }

  public void excluirCliente(Long id) {
    clienteRepository.deleteById(id);
  }
}
