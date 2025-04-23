package com.JSCode.Gestion_De_Ordenes.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orden_devolucion")
public class OrdenDevolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private Ordenes orden;

    private LocalDateTime fechaDevolucion;

    private String motivoDevolucion;

    private String estadoDevolucion;

    @Column(precision = 15, scale = 2)
    private BigDecimal totalReembolso;

    @OneToMany(mappedBy = "ordenDevolucion", cascade = CascadeType.ALL)
    private List<ImagenesDevolucion> imagenes;


}
