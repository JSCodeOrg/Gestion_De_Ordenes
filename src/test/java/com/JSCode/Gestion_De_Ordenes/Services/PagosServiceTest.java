package com.JSCode.Gestion_De_Ordenes.Services;

import com.JSCode.Gestion_De_Ordenes.dto.compras.productos.productoCantidadDTO;
import com.JSCode.Gestion_De_Ordenes.dto.compras.productos.productoDTO;
import com.JSCode.Gestion_De_Ordenes.dto.ordenes.AddressDTO;
import com.JSCode.Gestion_De_Ordenes.models.Ordenes;
import com.JSCode.Gestion_De_Ordenes.security.JwtUtil;
import com.JSCode.Gestion_De_Ordenes.services.MercadoPagoService;
import com.JSCode.Gestion_De_Ordenes.services.PagosService;
import com.JSCode.Gestion_De_Ordenes.services.ProductosService;
import com.JSCode.Gestion_De_Ordenes.services.UserService;
import com.mercadopago.exceptions.MPException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PagosServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private MercadoPagoService mpService;

    @Mock
    private ProductosService productosService;

    @Mock
    private UserService userService;

    @InjectMocks
    private PagosService pagosService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private productoDTO crearProductoDTO(Long id, int cantidad) {
        productoDTO dto = new productoDTO();
        dto.setProductoId(id);
        dto.setCantidad(cantidad);
        return dto;
    }

    @Test
    public void testGeneratePaymentReference_Exito() throws Exception {
        String token = "Bearer test.token";
        List<productoDTO> productos = List.of(crearProductoDTO(1L, 2));

        AddressDTO direccion = new AddressDTO();
        Ordenes orden = new Ordenes();
        orden.setId(123L);

        when(userService.getShippingAddress(token)).thenReturn(direccion);
        when(jwtUtil.extractUsername("test.token")).thenReturn("45");
        when(productosService.verificarExistencias(any(), eq(token))).thenReturn(true);
        when(productosService.crearNuevaOrden(eq(45L), eq(productos), eq(direccion))).thenReturn(orden);
        when(mpService.crearPreferenciaPago(productos, 123L)).thenReturn("https://mp.url/pago");

        ResponseEntity<String> response = pagosService.generatePaymentReference(token, productos);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("https://mp.url/pago", response.getBody());
    }

    @Test
    public void testGeneratePaymentReference_SinExistencias() throws Exception {
        String token = "Bearer token";
        List<productoDTO> productos = List.of(crearProductoDTO(1L, 1));

        when(userService.getShippingAddress(token)).thenReturn(new AddressDTO());
        when(jwtUtil.extractUsername("token")).thenReturn("12");
        when(productosService.verificarExistencias(any(), eq(token))).thenReturn(false);

        ResponseEntity<String> response = pagosService.generatePaymentReference(token, productos);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("No hay suficientes existencias"));
    }

    @Test
    public void testGeneratePaymentReference_MPException() throws Exception {
        String token = "Bearer token";
        List<productoDTO> productos = List.of(crearProductoDTO(1L, 1));

        AddressDTO direccion = new AddressDTO();
        Ordenes orden = new Ordenes();
        orden.setId(123L);  // ✅ Necesario para que se intente llamar a mpService

        when(userService.getShippingAddress(token)).thenReturn(direccion);
        when(jwtUtil.extractUsername("token")).thenReturn("22");
        when(productosService.verificarExistencias(any(), eq(token))).thenReturn(true);
        when(productosService.crearNuevaOrden(anyLong(), eq(productos), any())).thenReturn(orden);
        when(mpService.crearPreferenciaPago(any(), anyLong())).thenThrow(new MPException("Error MP"));

        ResponseEntity<String> response = pagosService.generatePaymentReference(token, productos);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Error al generar preferencia de pago"));
    }

    @Test
    public void testGeneratePaymentReference_ExceptionGenerica() throws Exception {
        String token = "Bearer token";
        List<productoDTO> productos = List.of(crearProductoDTO(1L, 1));

        when(userService.getShippingAddress(token)).thenThrow(new RuntimeException("Error general"));

        ResponseEntity<String> response = pagosService.generatePaymentReference(token, productos);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Error inesperado"));
    }
}
