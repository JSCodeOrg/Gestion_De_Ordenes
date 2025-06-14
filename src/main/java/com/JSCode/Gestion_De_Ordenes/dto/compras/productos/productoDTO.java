package com.JSCode.Gestion_De_Ordenes.dto.compras.productos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class productoDTO {
    private long productoId;
    private String nombre;
    private int cantidad;
    private BigDecimal precioUnitario;
    
}
