package com.DigitalBank.DBank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transferencia {

    private String contaOrigem;
    private String contaDestino;
    private double valor;

}
