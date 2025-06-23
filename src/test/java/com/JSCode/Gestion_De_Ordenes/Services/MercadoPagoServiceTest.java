package com.JSCode.Gestion_De_Ordenes.Services;

import com.JSCode.Gestion_De_Ordenes.dto.compras.productos.productoDTO;
import com.JSCode.Gestion_De_Ordenes.services.MercadoPagoService;
import com.JSCode.Gestion_De_Ordenes.services.ProductosService;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.resources.preference.Preference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MercadoPagoServiceTest {

    @Mock
    private ProductosService productosService;

    @Mock
    private PreferenceClient preferenceClient;

    @InjectMocks
    private MercadoPagoService mercadoPagoService;

    private MercadoPagoService mercadoPagoServiceSpy;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mercadoPagoServiceSpy = Mockito.spy(mercadoPagoService);
        // Inyectamos el token manualmente
        org.springframework.test.util.ReflectionTestUtils.setField(mercadoPagoServiceSpy, "accessToken", "TEST-TOKEN");
        doReturn(preferenceClient).when(mercadoPagoServiceSpy).getPreferenceClient();
    }

    @Test
    public void testCrearPreferenciaPago_Success() throws Exception {
        productoDTO producto = new productoDTO();
        producto.setNombre("Producto Test");
        producto.setCantidad(1);
        producto.setPrecioUnitario(10000.0); // <- este método ahora está corregido

        Preference mockPreference = mock(Preference.class);
        when(mockPreference.getInitPoint()).thenReturn("https://fake-init-point.com");

        when(preferenceClient.create(any(PreferenceRequest.class))).thenReturn(mockPreference);

        String result = mercadoPagoServiceSpy.crearPreferenciaPago(List.of(producto), 123L);

        assertEquals("https://fake-init-point.com", result);
        verify(preferenceClient, times(1)).create(any(PreferenceRequest.class));
    }

    @Test
    public void testCrearPreferenciaPago_Error() throws Exception {
        productoDTO producto = new productoDTO();
        producto.setNombre("Producto Test");
        producto.setCantidad(1);
        producto.setPrecioUnitario(5000.0);

        when(preferenceClient.create(any(PreferenceRequest.class)))
                .thenThrow(new RuntimeException("Error al crear preferencia"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            mercadoPagoServiceSpy.crearPreferenciaPago(List.of(producto), 456L);
        });

        assertTrue(exception.getMessage().contains("Error al crear preferencia"));
    }
}

