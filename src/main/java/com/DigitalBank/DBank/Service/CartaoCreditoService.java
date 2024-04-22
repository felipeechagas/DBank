package com.DigitalBank.DBank.Service;

import com.DigitalBank.DBank.Repository.CartaoCreditoRepository;
import com.DigitalBank.DBank.Repository.ClienteRepository;
import com.DigitalBank.DBank.exception.BadRequestException;
import com.DigitalBank.DBank.model.CartaoCredito;
import com.DigitalBank.DBank.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CartaoCreditoService {

    @Autowired
    private CartaoCreditoRepository cartaoCreditoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<CartaoCredito> listarCartaoCredito() {
        return cartaoCreditoRepository.findAll();
    }

    public CartaoCredito criarCartaoCreditoParaClienteExistente(CartaoCredito cartaoCredito) {
        Cliente clienteAssociado = cartaoCredito.getCliente();

        // Verifica se o cliente associado possui CPF
        if (clienteAssociado.getCpf() != null) {
            // Verifica se o cliente existe no banco de dados pelo CPF
            Optional<Cliente> optionalCliente = clienteRepository.findByCpf(clienteAssociado.getCpf());
            if (optionalCliente.isPresent()) {
                Cliente clienteExistente = optionalCliente.get();
                // Verifica se o cliente já possui um cartão de crédito
                if (clienteExistente.getCartaoCredito() == null) {
                    // Atribui o cliente existente ao cartão de crédito
                    cartaoCredito.setCliente(clienteExistente);

                    // Gera um número de cartão automaticamente
                    Random random = new Random();
                    StringBuilder numeroCartaoBuilder = new StringBuilder();
                    for (int i = 0; i < 16; i++) {
                        int digit = random.nextInt(10);
                        numeroCartaoBuilder.append(digit);
                        if ((i + 1) % 4 == 0 && i != 15) {
                            numeroCartaoBuilder.append("-");
                        }
                    }
                    cartaoCredito.setNumeroCartao(numeroCartaoBuilder.toString());

                    // Gera um código de segurança (CVV) de três dígitos aleatório
                    int codigoSeguranca = random.nextInt(1000);
                    cartaoCredito.setCodigoSeguranca(codigoSeguranca);

                    // Define a data de validade do cartão (5 anos a partir da data de criação do cartão)
                    Calendar calendarValidade = Calendar.getInstance();
                    calendarValidade.setTime(new Date());
                    calendarValidade.add(Calendar.YEAR, 5);
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
                // Cria o cliente se não existir
                Cliente novoCliente = clienteRepository.save(clienteAssociado);
                cartaoCredito.setCliente(novoCliente);

                // Gera um número de cartão automaticamente
                Random random = new Random();
                StringBuilder numeroCartaoBuilder = new StringBuilder();
                for (int i = 0; i < 16; i++) {
                    int digit = random.nextInt(10);
                    numeroCartaoBuilder.append(digit);
                    if ((i + 1) % 4 == 0 && i != 15) {
                        numeroCartaoBuilder.append("-");
                    }
                }
                cartaoCredito.setNumeroCartao(numeroCartaoBuilder.toString());

                // Gera um código de segurança (CVV) de três dígitos aleatório
                int codigoSeguranca = random.nextInt(1000);
                cartaoCredito.setCodigoSeguranca(codigoSeguranca);

                // Define a data de validade do cartão (5 anos a partir da data de criação do cartão)
                Calendar calendarValidade = Calendar.getInstance();
                calendarValidade.setTime(new Date());
                calendarValidade.add(Calendar.YEAR, 5);
                Date validadeCartao = calendarValidade.getTime();
                cartaoCredito.setValidade(new SimpleDateFormat("MM/yyyy").format(validadeCartao));

                // Define o status do cartão como ativo
                cartaoCredito.setStatus(CartaoCredito.Status.ATIVO);

                // Salva o cartão de crédito no banco de dados
                return cartaoCreditoRepository.save(cartaoCredito);
            }
        } else {
            throw new BadRequestException("CPF do cliente não fornecido. Não é possível criar o cartão de crédito.");
        }
    }


    public void alterarVencimentoFatura(String numeroCartao, String vencimentoFatura) {
        Optional<CartaoCredito> optionalCartaoCredito = cartaoCreditoRepository.findByNumeroCartao(numeroCartao);

        if (optionalCartaoCredito.isPresent()) {
            CartaoCredito cartaoCredito = optionalCartaoCredito.get();
            cartaoCredito.setVencimentoFatura(vencimentoFatura);
            cartaoCreditoRepository.save(cartaoCredito);
        } else {
            throw new BadRequestException("Cartão de crédito não encontrado.");
        }
    }

    public void cancelarCartaoCredito(String numeroCartao) {
        Optional<CartaoCredito> optionalCartaoCredito = cartaoCreditoRepository.findByNumeroCartao(numeroCartao);

        if (optionalCartaoCredito.isPresent()) {
            CartaoCredito cartaoCredito = optionalCartaoCredito.get();

            if (cartaoCredito.getStatus() == CartaoCredito.Status.CANCELADO) {
                throw new BadRequestException("O cartão já foi cancelado.");
            }

            if (cartaoCredito.getLimiteCreditoUtilizado() == 0) {
                cartaoCredito.setStatus(CartaoCredito.Status.CANCELADO);
                cartaoCreditoRepository.save(cartaoCredito);
            } else {
                throw new BadRequestException("Não é possível cancelar o cartão. Limite de crédito utilizado.");
            }
        } else {
            throw new BadRequestException("Cartão de crédito não encontrado.");
        }
    }


    public void bloquearCartaoCredito(String numeroCartao) {
        Optional<CartaoCredito> optionalCartaoCredito = cartaoCreditoRepository.findByNumeroCartao(numeroCartao);

        if (optionalCartaoCredito.isPresent()) {
            CartaoCredito cartaoCredito = optionalCartaoCredito.get();

            if (cartaoCredito.getStatus() == CartaoCredito.Status.BLOQUEADO) {
                throw new BadRequestException("O cartão já está bloqueado.");
            }

            if (cartaoCredito.getStatus() == CartaoCredito.Status.CANCELADO) {
                throw new BadRequestException("Não é possível bloquear um cartão cancelado.");
            }

            cartaoCredito.setStatus(CartaoCredito.Status.BLOQUEADO);
            cartaoCreditoRepository.save(cartaoCredito);
        } else {
            throw new BadRequestException("Cartão de crédito não encontrado.");
        }
    }

    public void desbloquearCartaoCredito(String numeroCartao) {
        Optional<CartaoCredito> optionalCartaoCredito = cartaoCreditoRepository.findByNumeroCartao(numeroCartao);

        if (optionalCartaoCredito.isPresent()) {
            CartaoCredito cartaoCredito = optionalCartaoCredito.get();

            if (cartaoCredito.getStatus() == CartaoCredito.Status.ATIVO) {
                throw new BadRequestException("O cartão já está desbloqueado.");
            }

            if (cartaoCredito.getStatus() == CartaoCredito.Status.CANCELADO) {
                throw new BadRequestException("Não é possível desbloquear um cartão cancelado.");
            }

            cartaoCredito.setStatus(CartaoCredito.Status.ATIVO);
            cartaoCreditoRepository.save(cartaoCredito);
        } else {
            throw new BadRequestException("Cartão de crédito não encontrado.");
        }
    }


}
