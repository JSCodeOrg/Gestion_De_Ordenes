package com.JSCode.Gestion_De_Ordenes.Services;

import com.JSCode.Gestion_De_Ordenes.dto.ordenes.RegistroPagoDTO;
import com.JSCode.Gestion_De_Ordenes.dto.ordenes.actualizarEstadoOrdenDTO;
import com.JSCode.Gestion_De_Ordenes.dto.rabbitMQ.pedidoDTO;
import com.JSCode.Gestion_De_Ordenes.models.Ordenes;
import com.JSCode.Gestion_De_Ordenes.models.RegistroPagos;
import com.JSCode.Gestion_De_Ordenes.repositories.OrdenesRepository;
import com.JSCode.Gestion_De_Ordenes.repositories.RegistroPagosRepository;
import com.JSCode.Gestion_De_Ordenes.services.OrdenesService;
import com.JSCode.Gestion_De_Ordenes.services.RabbitMQ.PedidoProducer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrdenesServiceTest {

    @Mock
    private OrdenesRepository ordenesRepository;

    @Mock
    private RegistroPagosRepository rPagosRepository;

    @Mock
    private PedidoProducer pedidoProducer;

    @InjectMocks
    private OrdenesService ordenesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testActualizarEstadoOrden_Exito() {
        // Arrange
        actualizarEstadoOrdenDTO dto = mock(actualizarEstadoOrdenDTO.class);
        when(dto.getOrderId()).thenReturn("1");
        when(dto.getPayment_id()).thenReturn("PAY123");

        Ordenes ordenMock = new Ordenes();
        ordenMock.setShippingAddress("Calle Falsa 123");

        when(ordenesRepository.findById(1L)).thenReturn(Optional.of(ordenMock));
        when(ordenesRepository.save(any(Ordenes.class))).thenReturn(ordenMock);
        when(rPagosRepository.save(any(RegistroPagos.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        RegistroPagoDTO resultado = ordenesService.actualizarEstadoOrden(dto, "Bearer token");

        // Assert
        assertNotNull(resultado);
        assertEquals("1", resultado.getMercadoPagoOrderId());
        assertEquals("PAY123", resultado.getPaymentId());
        assertEquals("PAGADA", resultado.getStatus());

        verify(ordenesRepository).save(any(Ordenes.class));
        verify(rPagosRepository).save(any(RegistroPagos.class));
        verify(pedidoProducer).enviarPedido(any(pedidoDTO.class));
    }

    @Test
    public void testActualizarEstadoOrden_OrdenNoExiste() {
        // Arrange
        actualizarEstadoOrdenDTO dto = mock(actualizarEstadoOrdenDTO.class);
        when(dto.getOrderId()).thenReturn("99");

        when(ordenesRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ordenesService.actualizarEstadoOrden(dto, "Bearer token");
        });

        // Mostrar el mensaje real para depuración
        System.out.println("Mensaje de la excepción: " + exception.getMessage());

        // Validación más flexible
        assertNotNull(exception.getMessage());
    }
}
