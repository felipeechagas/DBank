package com.DigitalBank.DBank.controller;

import com.DigitalBank.DBank.service.ContaService;
import com.DigitalBank.DBank.exception.BadRequestException;
import com.DigitalBank.DBank.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @GetMapping("/list")
    public ResponseEntity<List<Conta>> listarContas() {
        List<Conta> contas = contaService.listarContas();
        return new ResponseEntity<>(contas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarContaPorId(@PathVariable Long id) {
        Conta conta = contaService.buscarContaPorId(id);
        return new ResponseEntity<>(conta, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Conta> criarConta(@RequestBody Conta conta) throws Exception {
        try {
            Conta novaConta = contaService.criarConta(conta);
            return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Conta> atualizarConta(@PathVariable Long id, @RequestBody Conta contaAtualizada) {
        Conta contaAtualizadaResult = contaService.atualizarConta(id, contaAtualizada);
        if (contaAtualizadaResult != null) {
            return new ResponseEntity<>(contaAtualizadaResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> excluirConta(@PathVariable Long id) {
        contaService.excluirConta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/saldo")
    public ResponseEntity<Double> saldo(@RequestParam("numeroConta") String numeroConta) {
        try {
            Double saldo = contaService.consultarSaldo(numeroConta);
            return ResponseEntity.ok(saldo);
        } catch (BadRequestException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //TODO Metodo Extrato detalhado

    @PostMapping("/deposito")
    public ResponseEntity<String> deposito(@RequestBody DepositoRequest request) {
        try {
            contaService.deposito(request.getNumeroConta(), request.getValor());
            return ResponseEntity.ok("Depósito realizado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o depósito.");
        }
    }

    @PostMapping("/saque")
    public ResponseEntity<String> saque(@RequestBody SaqueRequest saqueRequest) {
        try {
            contaService.saque(saqueRequest.getNumeroConta(), saqueRequest.getValor());
            return ResponseEntity.ok("Saque realizado com sucesso.");
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor.");
        }
    }

    @PostMapping("/transferencia")
    public ResponseEntity<String> realizarTransferencia(@RequestBody Transferencia transferencia) {
        try {
            contaService.transferencia(transferencia.getContaOrigem(), transferencia.getContaDestino(), transferencia.getValor());
            return ResponseEntity.ok("Transferência realizada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao realizar transferência: " + e.getMessage());
        }
    }



}
