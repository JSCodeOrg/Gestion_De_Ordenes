package com.JSCode.Gestion_De_Ordenes.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "registro_pagos")
public class RegistroPagos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentId;

    private String status;

    private String mercadoPagoOrderId;

    private LocalDateTime fechaPago;

    @OneToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private Ordenes orden;

}
