package com.DigitalBank.DBank.Service;

import com.DigitalBank.DBank.Repository.ClienteRepository;
import com.DigitalBank.DBank.Repository.ContaRepository;
import com.DigitalBank.DBank.exception.BadRequestException;
import com.DigitalBank.DBank.model.CartaoCredito;
import com.DigitalBank.DBank.model.Cliente;
import com.DigitalBank.DBank.model.Conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
