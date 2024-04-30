package com.DigitalBank.DBank.service;

import com.DigitalBank.DBank.repository.TransacaoRepository;
import com.DigitalBank.DBank.exception.BadRequestException;
import com.DigitalBank.DBank.model.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExtratoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Transacao> extrato(String numeroConta, Integer mes) {
        LocalDate dataAtual = LocalDate.now();
        int mesAtual = dataAtual.getMonthValue();

        if (mes == null) {
            // Se o mês não for informado, considera o mês atual
            return extrato(numeroConta, mesAtual);
        } else if (mes < 1 || mes > 12) {
            // Se o mês informado for inválido, lança uma exceção
            throw new BadRequestException("Mês inválido. Informe um valor entre 1 e 12.");
        } else {
            // Se o mês for informado, retorna desde o mês informado até o mês atual
            return extrato(numeroConta, mes);
        }
    }
}
