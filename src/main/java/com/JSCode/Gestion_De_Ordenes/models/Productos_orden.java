package com.JSCode.Gestion_De_Ordenes.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "productos_orden")
public class Productos_orden {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private Ordenes order;

    @Column(name = "producto_id", nullable = false)
    private Long productId;

    @Column(name = "nombre_producto", nullable = false)
    private String productName;

    @Column(name = "precio_producto", nullable = false, precision = 15, scale = 2)
    private BigDecimal productPrice;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private BigDecimal total;
    
}
