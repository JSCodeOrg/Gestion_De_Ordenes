package com.JSCode.Gestion_De_Ordenes.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ordenes")
public class Ordenes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderCode;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    private String shippingAddress;

    private Long userId;

    private LocalDateTime createdAt;

    private String status;

    private String preferenceId;

    // ✅ Campo adicional para corregir error en orden.setFechaPago(...)
    private LocalDateTime fechaPago;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Productos_orden> products;

    // ✅ Opcional: si necesitas getOrderId() en lugar de getId()
    public Long getOrderId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
