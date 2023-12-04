package com.DigitalBank.DBank.Service;

import com.DigitalBank.DBank.Repository.ClienteRepository;
import com.DigitalBank.DBank.Repository.ContaRepository;
import com.DigitalBank.DBank.exception.BadRequestException;
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
        Cliente clienteAssociado = conta.getCliente();

        // Verifica se o cliente associado já possui um ID
        if (clienteAssociado.getId() == null) {
            Cliente novoCliente = clienteRepository.save(clienteAssociado);
            conta.setCliente(novoCliente);
        } else if (clienteRepository.findById(clienteAssociado.getId()).isEmpty()) {
            throw new BadRequestException("Cliente não encontrado. Não é possível criar a conta.");
        }

        Random random = new Random();
        String numeroConta = String.format("%04d-%01d", random.nextInt(10000), random.nextInt(10));
        conta.setNumero(numeroConta);
        conta.setDataCriacao(new Date());

        return contaRepository.save(conta);
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
