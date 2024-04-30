package com.DigitalBank.DBank.service;

import com.DigitalBank.DBank.repository.ClienteRepository;
import com.DigitalBank.DBank.repository.ContaRepository;
import com.DigitalBank.DBank.repository.TransacaoDetalhadaRepository;
import com.DigitalBank.DBank.repository.TransacaoRepository;
import com.DigitalBank.DBank.exception.BadRequestException;
import com.DigitalBank.DBank.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TransacaoDetalhadaRepository transacaoDetalhadaRepository;

    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }

    public Conta buscarContaPorId(Long id) {
        return contaRepository.findById(id).orElse(null);
    }

    public Conta criarConta(Conta conta) {
        try {
            Cliente clienteAssociado = conta.getCliente();

            // Verifica se o cliente associado já possui uma conta
            if (clienteAssociado.getConta() != null) {
                throw new BadRequestException("Cliente já possui uma conta. Não é possível criar outra conta.");
            }

            // Verifica se o cliente associado já existe com base no CPF
            String cpf = clienteAssociado.getCpf();
            Optional<Cliente> clienteExistente = clienteRepository.findByCpf(cpf);

            if (clienteExistente.isPresent()) {
                Cliente cliente = clienteExistente.get();
                if (cliente.getCartaoCredito() != null) {
                    conta.setCliente(cliente);

                    if (cliente.getCartaoCredito() != null) {
                        CartaoCredito cartaoCredito = cliente.getCartaoCredito();
                        conta.setCartaoCredito(cartaoCredito);
                    }
                }
            } else {
                // Se o cliente não existe, salva o cliente no banco de dados
                clienteAssociado = clienteRepository.save(clienteAssociado);
                conta.setCliente(clienteAssociado);
            }

            // Gera um número de conta aleatório
            Random random = new Random();
            String numeroConta = String.format("%04d-%01d", random.nextInt(10000), random.nextInt(10));
            conta.setNumero(numeroConta);

            // Define a data de criação da conta como a data atual
            conta.setDataCriacao(new Date());

            // Salva a conta no banco de dados
            return contaRepository.save(conta);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro interno ao criar nova conta");
        }
    }

    public Conta atualizarConta(Long id, Conta contaAtualizada) {
        Conta contaExistente = contaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Conta não encontrado com o ID: " + id));

            contaExistente.setAtiva(contaAtualizada.isAtiva());
            contaExistente.setTipoConta(contaAtualizada.getTipoConta());
            contaExistente.setSaldo(contaAtualizada.getSaldo());

            return contaRepository.save(contaExistente);
    }

    public void excluirConta(Long id) {
        Conta contaParaExcluir = contaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Conta não encontrada com o ID: " + id));

        Cliente clienteAssociado = contaParaExcluir.getCliente();

        if (clienteAssociado != null) {
            // Remover a referência da conta no cliente
            clienteAssociado.setConta(null);

            // Salvar as alterações no cliente
            clienteRepository.save(clienteAssociado);
        }

        // Excluir a conta
        contaRepository.deleteById(id);
    }

    public Double consultarSaldo(String numeroConta) {
        Conta conta = contaRepository.findByNumero(numeroConta);

        if (conta != null) {
            return conta.getSaldo();
        } else {
            throw new BadRequestException("Conta não encontrada.");
        }
    }

    @Transactional
    public void deposito(String numeroConta, double valor) {
        // Busca a conta pelo número
        Conta conta = contaRepository.findByNumero(numeroConta);

        // Verifica se a conta foi encontrada
        if (conta == null) {
            throw new BadRequestException("Conta não encontrada para o número: " + numeroConta);
        }

        // Realiza o depósito
        conta.depositar(valor);

        // Salva as alterações no banco de dados
        contaRepository.save(conta);
    }

    public void saque(String numeroConta, double valor) {
        Conta conta = contaRepository.findByNumero(numeroConta);

        if (conta == null) {
            throw new BadRequestException("Conta não encontrada para o número: " + numeroConta);
        }

        double saldoAtual = conta.getSaldo();

        if (saldoAtual < valor) {
            throw new BadRequestException("Saldo insuficiente para efetuar o saque na conta: " + numeroConta);
        }

        double novoSaldo = saldoAtual - valor;
        conta.setSaldo(novoSaldo);

        Transacao transacao = new Transacao();
        transacao.setConta(conta);
        transacao.setTipoTransacao("SAQUE");
        transacao.setDataHora(LocalDateTime.now());
        transacao.setValor(valor);

        transacaoRepository.save(transacao);
    }

    public void transferencia(String numeroContaOrigem, String numeroContaDestino, double valor) {
        // Obter contas de origem e destino do banco de dados
        Conta contaOrigem = contaRepository.findByNumero(numeroContaOrigem);
        Conta contaDestino = contaRepository.findByNumero(numeroContaDestino);

        // Verificar se as contas existem
        if (contaOrigem == null || contaDestino == null) {
            throw new BadRequestException("Conta de origem ou destino não encontrada.");
        }

        // Verificar se a conta de origem tem saldo suficiente
        if (contaOrigem.getSaldo() < valor) {
            throw new BadRequestException("Saldo insuficiente na conta de origem.");
        }

        // Atualizar os saldos das contas
        contaOrigem.debitar(valor);
        contaDestino.creditar(valor);

        // Registrar a transação
        Transacao transacao = new Transacao();
        transacao.setContaOrigem(contaOrigem);
        transacao.setContaDestino(contaDestino);
        transacao.setValor(valor);
        transacao.setDataHora(LocalDateTime.now());
        transacaoRepository.save(transacao);
    }





}
