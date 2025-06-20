package com.JSCode.Gestion_De_Ordenes.services;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.JSCode.Gestion_De_Ordenes.dto.compras.productos.productoCantidadDTO;
import com.JSCode.Gestion_De_Ordenes.dto.compras.productos.productoDTO;
import com.JSCode.Gestion_De_Ordenes.models.Ordenes;
import com.JSCode.Gestion_De_Ordenes.security.JwtUtil;
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

    @Autowired
    private UserService userService;

    public ResponseEntity<String> generatePaymentReference(String token, List<productoDTO> productos) {
        try {

            for(productoDTO producto : productos){
                System.out.println(producto.getProductoId());
                System.out.println(producto.getCantidad());
            }

            String shippingAddress = userService.getShippingAddress(token);

            String clean_token = token.substring(7);
            String user_id = jwtUtil.extractUsername(clean_token);
            Long user_id_long = Long.parseLong(user_id);

            List<productoCantidadDTO> productosVerificarCantidad = new ArrayList<>();

            for (productoDTO producto : productos) {
                productoCantidadDTO productoCantidad = new productoCantidadDTO();
                productoCantidad.setIdProducto(producto.getProductoId());
                productoCantidad.setCantidad(producto.getCantidad());
                productosVerificarCantidad.add(productoCantidad);
            }

            Boolean existenciasValidas = productosService.verificarExistencias(productosVerificarCantidad, token);
            if (!existenciasValidas) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No hay suficientes existencias para los productos seleccionados.");
            }

            Ordenes nuevaOrden = productosService.crearNuevaOrden(user_id_long, productos, shippingAddress);

            Long ordenId = nuevaOrden.getId();

            String paymentUrl = mpService.crearPreferenciaPago(productos, ordenId);

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
