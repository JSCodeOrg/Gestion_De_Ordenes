package com.JSCode.Gestion_De_Ordenes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JSCode.Gestion_De_Ordenes.dto.compras.productos.productoDTO;
import com.JSCode.Gestion_De_Ordenes.security.JwtUtil;
import com.JSCode.Gestion_De_Ordenes.services.PagosService;

@RestController
@RequestMapping("/pagos")
public class PagosController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PagosService pagosService;

    @PostMapping
    public ResponseEntity generarNuevoPago(@RequestBody List<productoDTO> productos,
            @RequestHeader("Authorization") String authToken) {
            
                System.out.println("Productos recibidos: " + productos);

        String token = authToken.substring(7);

        try {
            ResponseEntity<String> paymentUrl = pagosService.generatePaymentReference(token, productos);

            return paymentUrl;
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ha ocurrido un error al generar la preferencia de pago");

        }

    }

}
