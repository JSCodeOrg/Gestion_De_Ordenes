package com.JSCode.Gestion_De_Ordenes.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.JSCode.Gestion_De_Ordenes.dto.compras.productos.productoCantidadDTO;
import com.JSCode.Gestion_De_Ordenes.dto.compras.productos.productoDTO;
import com.JSCode.Gestion_De_Ordenes.security.JwtUtil;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.HttpStatus;

@Service
public class PagosService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MercadoPagoService mpService;

    @Autowired
    private ProductosService productosService;

    public ResponseEntity<String> generatePaymentReference(String token, List<productoDTO> productos) {
        try {
            String user_id = this.jwtUtil.extractUsername(token);
            Long user_id_long = Long.parseLong(user_id);
        
            List<productoCantidadDTO> productosVerificarCantidad = new ArrayList<>();

            for(productoDTO producto: productos){
                productoCantidadDTO productoCantidad = new productoCantidadDTO();
                productoCantidad.setIdProducto(producto.getIdProducto());
                productoCantidad.setCantidad(producto.getCantidad());
                productosVerificarCantidad.add(productoCantidad);
            }

            Boolean existenciasValidas = productosService.verificarExistencias(productosVerificarCantidad);
            if (!existenciasValidas) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No hay suficientes existencias para los productos seleccionados.");
            }
            
            String paymentUrl = mpService.crearPreferenciaPago(productos);

            return ResponseEntity.ok(paymentUrl);

        } catch (MPException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar preferencia de pago: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error inesperado: " + e.getMessage());
        }
    }



}
