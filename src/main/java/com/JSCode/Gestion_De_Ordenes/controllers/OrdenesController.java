package com.JSCode.Gestion_De_Ordenes.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.JSCode.Gestion_De_Ordenes.dto.ordenes.actualizarEstadoOrdenDTO;
import com.JSCode.Gestion_De_Ordenes.models.RegistroPagos;
import com.JSCode.Gestion_De_Ordenes.services.OrdenesService;
import jakarta.ws.rs.NotFoundException;

@RestController
@RequestMapping("/ordenes")
public class OrdenesController {

    @Autowired
    private OrdenesService ordenesService;

    @PatchMapping()
    public ResponseEntity<?> cambiarEstadoOrden(@RequestBody actualizarEstadoOrdenDTO orden,

            @RequestHeader("Authorization") String authToken) {

        try {
            RegistroPagos orden_paga = this.ordenesService.actualizarEstadoOrden(orden, authToken);

            return ResponseEntity.ok(orden_paga);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body("Orden no encontrada: " + e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error desconocido al actualizar la orden." + e.getMessage());
        }

    }

}
