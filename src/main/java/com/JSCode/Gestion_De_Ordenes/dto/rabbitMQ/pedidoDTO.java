package com.JSCode.Gestion_De_Ordenes.dto.rabbitMQ;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class pedidoDTO {
    private Long id;
    private String shippingAddress;
}
