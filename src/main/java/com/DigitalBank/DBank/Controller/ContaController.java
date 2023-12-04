package com.DigitalBank.DBank.Controller;

import com.DigitalBank.DBank.Service.ContaService;
import com.DigitalBank.DBank.exception.BadRequestException;
import com.DigitalBank.DBank.model.Conta;
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
    public ResponseEntity<Conta> criarConta(@RequestBody Conta conta) {
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
}
