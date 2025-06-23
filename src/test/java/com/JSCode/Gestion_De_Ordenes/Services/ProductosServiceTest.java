package com.JSCode.Gestion_De_Ordenes.Services;

import com.JSCode.Gestion_De_Ordenes.dto.compras.productos.productoCantidadDTO;
import com.JSCode.Gestion_De_Ordenes.dto.compras.productos.productoDTO;
import com.JSCode.Gestion_De_Ordenes.dto.ordenes.AddressDTO;
import com.JSCode.Gestion_De_Ordenes.models.Ordenes;
import com.JSCode.Gestion_De_Ordenes.models.Productos_orden;
import com.JSCode.Gestion_De_Ordenes.repositories.OrdenesRepository;
import com.JSCode.Gestion_De_Ordenes.services.ProductosService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductosServiceTest {

    @Mock
    private OrdenesRepository ordenesRepository;

    @InjectMocks
    private ProductosService productosService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test para crearNuevaOrden
    @Test
    public void testCrearNuevaOrden_Exito() {
        // Arrange
        Long userId = 1L;
        AddressDTO direccion = new AddressDTO();
        direccion.setDireccion("Calle Falsa 123");
        direccion.setCiudad("Springfield");

        productoDTO producto1 = new productoDTO();
        producto1.setProductoId(10L);
        producto1.setCantidad(2);
        producto1.setNombre("Producto 1");
        producto1.setPrecioUnitario(50.00);

        productoDTO producto2 = new productoDTO();
        producto2.setProductoId(20L);
        producto2.setCantidad(1);
        producto2.setNombre("Producto 2");
        producto2.setPrecioUnitario(30.00);

        List<productoDTO> productos = List.of(producto1, producto2);

        // Act
        Ordenes orden = productosService.crearNuevaOrden(userId, productos, direccion);

        // Assert
        assertNotNull(orden.getOrderCode());
        assertEquals("Calle Falsa 123, Springfield", orden.getShippingAddress());
        assertEquals(userId, orden.getUserId());
        assertEquals("PENDIENTE", orden.getStatus());
        assertEquals(0, orden.getTotalAmount().compareTo(new BigDecimal("130.00")));

        assertEquals(2, orden.getProducts().size());
        Productos_orden p1 = orden.getProducts().get(0);
        assertEquals("Producto 1", p1.getProductName());
        assertEquals(2, p1.getCantidad());
    }

    // Test de verificarExistencias con RestTemplate simulado (mock parcial)
    @Test
    public void testVerificarExistencias_True() {
        // Arrange
        RestTemplate restTemplate = mock(RestTemplate.class);
        List<productoCantidadDTO> productos = List.of(new productoCantidadDTO());

        String token = "Bearer test.token";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<List<productoCantidadDTO>> request = new HttpEntity<>(productos, headers);

        ResponseEntity<Boolean> responseEntity = new ResponseEntity<>(true, HttpStatus.OK);

        // Simular el exchange del restTemplate (mediante mockito static o injertado manualmente)
        ProductosService realService = new ProductosService() {
            @Override
            public Boolean verificarExistencias(List<productoCantidadDTO> productos, String token) {
                return responseEntity.getBody();
            }
        };

        // Act
        Boolean result = realService.verificarExistencias(productos, token);

        // Assert
        assertTrue(result);
    }
}
