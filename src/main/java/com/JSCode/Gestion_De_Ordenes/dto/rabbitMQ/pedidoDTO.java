// File: Gestion_De_Ordenes/src/main/java/com/JSCode/Gestion_De_Ordenes/dto/rabbitMQ/PedidoDTO.java
package com.JSCode.Gestion_De_Ordenes.dto.rabbitMQ;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {
    private Long id;
    private String shippingAddress;

}
