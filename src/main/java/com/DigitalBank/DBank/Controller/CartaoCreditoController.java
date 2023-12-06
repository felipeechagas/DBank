package com.DigitalBank.DBank.Controller;

import com.DigitalBank.DBank.Service.CartaoCreditoService;
import com.DigitalBank.DBank.Service.ClienteService;
import com.DigitalBank.DBank.exception.BadRequestException;
import com.DigitalBank.DBank.model.CartaoCredito;
import com.DigitalBank.DBank.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/cartaoCredito")
public class CartaoCreditoController {

    @Autowired
    private CartaoCreditoService cartaoCreditoService;

    @GetMapping("/list")
    public ResponseEntity<List<CartaoCredito>> listarCartaoCredito() {
        List<CartaoCredito> cartaoCreditos = cartaoCreditoService.listarCartaoCredito();
        return new ResponseEntity<>(cartaoCreditos, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CartaoCredito> criarCartaoCredito(@RequestBody CartaoCredito cartaoCredito) {
        try {
            cartaoCredito.setStatus(CartaoCredito.Status.ATIVO);
            CartaoCredito novoCartaoCredito = cartaoCreditoService.criarNovoCartaoCredito(cartaoCredito);
            return new ResponseEntity<>(novoCartaoCredito, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateVencimentoFatura")
    public ResponseEntity<String> alterarVencimentoFatura(@RequestParam String numeroCartao,
                                                          @RequestParam String vencimentoFatura) {
        try {
            cartaoCreditoService.alterarVencimentoFatura(numeroCartao, vencimentoFatura);
            return new ResponseEntity<>("Vencimento da fatura alterado com sucesso.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao alterar o vencimento da fatura.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cancelar")
    public ResponseEntity<String> cancelarCartaoCredito(@RequestParam String numeroCartao) {
        try {
            cartaoCreditoService.cancelarCartaoCredito(numeroCartao);
            return new ResponseEntity<>("Cartão de crédito cancelado com sucesso.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao cancelar o cartão de crédito.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/bloquear")
    public ResponseEntity<String> bloquearCartaoCredito(@RequestParam String numeroCartao) {
        try {
            cartaoCreditoService.bloquearCartaoCredito(numeroCartao);
            return new ResponseEntity<>("Cartão de crédito bloqueado com sucesso.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao bloquear o cartão de crédito.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/desbloquear")
    public ResponseEntity<String> desbloquearCartaoCredito(@RequestParam String numeroCartao) {
        try {
            cartaoCreditoService.desbloquearCartaoCredito(numeroCartao);
            return new ResponseEntity<>("Cartão de crédito desbloqueado com sucesso.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao desbloquear o cartão de crédito.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
