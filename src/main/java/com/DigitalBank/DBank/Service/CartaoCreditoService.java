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

    public CartaoCredito criarNovoCartaoCredito(CartaoCredito cartaoCredito) {

        Cliente clienteAssociado = cartaoCredito.getCliente();

        if (clienteAssociado.getId() == null) {
            Cliente novoCliente = clienteRepository.save(clienteAssociado);
            cartaoCredito.setCliente(novoCliente);
        } else if (clienteRepository.findById(clienteAssociado.getId()).isPresent()) {
            throw new BadRequestException("Cliente não encontrado. Não é possível criar o cartão de crédito.");
        }

        // Gera um número de cartão automaticamente
        Random random = new Random();
        String numeroCartao = String.format("%04d-%04d-%04d-%04d",
                random.nextInt(10000), random.nextInt(10000),
                random.nextInt(10000), random.nextInt(10000));
        cartaoCredito.setNumeroCartao(numeroCartao);

        // Gera um código de segurança (CVV) de três dígitos aleatório
        String codigoSeguranca = String.format("%03d", random.nextInt(1000));
        cartaoCredito.setCodigoSeguranca(Integer.valueOf(codigoSeguranca));

        // Define a data de criação como a data atual
        Calendar calendarValidade = Calendar.getInstance();
        calendarValidade.setTime(new Date());
        calendarValidade.add(Calendar.YEAR, 3);
        Date validadeCartao = calendarValidade.getTime();
        cartaoCredito.setValidade(new SimpleDateFormat("MM/yyyy").format(validadeCartao));

        String vencimentoFatura = cartaoCredito.getVencimentoFatura();

        // Calcula a data de validade da fatura com base nos dias fornecidos
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(vencimentoFatura));
        cartaoCredito.setVencimentoFatura(new SimpleDateFormat("dd").format(calendar.getTime()));

        cartaoCredito.setStatus(CartaoCredito.Status.ATIVO);

        return cartaoCreditoRepository.save(cartaoCredito);

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
