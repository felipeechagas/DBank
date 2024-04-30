package com.DigitalBank.DBank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaqueRequest {

    private String numeroConta;
    private double valor;

}

