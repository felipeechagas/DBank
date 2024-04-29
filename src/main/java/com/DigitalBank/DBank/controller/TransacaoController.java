package com.DigitalBank.DBank.controller;

import com.DigitalBank.DBank.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping("/sacar")
    public ResponseEntity<String> sacar(@RequestParam double valor, @RequestParam String numeroConta) {
        try {
            transacaoService.sacar(valor, numeroConta);
            return new ResponseEntity<>("Saque realizado com sucesso.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao processar o saque.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
