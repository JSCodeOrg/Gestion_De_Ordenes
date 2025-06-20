package com.JSCode.Gestion_De_Ordenes.services;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import com.JSCode.Gestion_De_Ordenes.dto.compras.productos.productoCantidadDTO;
import com.JSCode.Gestion_De_Ordenes.dto.compras.productos.productoDTO;
import com.JSCode.Gestion_De_Ordenes.models.Ordenes;
import com.JSCode.Gestion_De_Ordenes.models.Productos_orden;
import com.JSCode.Gestion_De_Ordenes.repositories.OrdenesRepository;

@Service
public class ProductosService {

    private final String inventarioUrl = "http://api-gateway:8080/inventario/existencias";

    @Autowired
    private OrdenesRepository ordenesRepository;

    public Boolean verificarExistencias(List<productoCantidadDTO> productos, String token) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        
        HttpEntity<List<productoCantidadDTO>> request = new HttpEntity<>(productos, headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(
                inventarioUrl,
                HttpMethod.POST,
                request,
                Boolean.class);
        return response.getBody();
    }

    public Ordenes crearNuevaOrden(Long user_id, List<productoDTO> productos, String shippingAddress) { 
        BigDecimal precio_total = BigDecimal.ZERO; 

        Ordenes orden = new Ordenes(); 
        orden.setOrderCode(UUID.randomUUID().toString()); 
        orden.setShippingAddress(shippingAddress); 
        orden.setUserId(user_id); 
        orden.setCreatedAt(LocalDateTime.now());
        orden.setStatus("PENDIENTE"); 

        List<Productos_orden> productosOrdenados = new ArrayList<>(); 

        for (productoDTO producto : productos) { 
            BigDecimal subtotal = producto.getPrecioUnitario().multiply(BigDecimal.valueOf(producto.getCantidad())); 
            precio_total = precio_total.add(subtotal); 

            Productos_orden productoOrden = new Productos_orden(); 
            productoOrden.setOrder(orden); 
            productoOrden.setProductId(producto.getProductoId());  
            productoOrden.setProductName(producto.getNombre()); 
            productoOrden.setProductPrice(producto.getPrecioUnitario()); 
            productoOrden.setCantidad(producto.getCantidad()); 
            productoOrden.setTotal(subtotal); 

            productosOrdenados.add(productoOrden); 
        }

        orden.setTotalAmount(precio_total); 
        orden.setProducts(productosOrdenados); 

        ordenesRepository.save(orden); 

        return orden; 
    }

}
