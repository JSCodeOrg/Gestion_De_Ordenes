package com.JSCode.Gestion_De_Ordenes.dto.ordenes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class actualizarEstadoOrdenDTO {
    private String orderId;
    private String status;
    private String payment_id;
}
