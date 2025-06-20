package com.JSCode.Gestion_De_Ordenes.dto.ordenes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroPagoDTO {

    private String paymentId;
    private String status;
    private String mercadoPagoOrderId;

}
