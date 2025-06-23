package com.JSCode.Gestion_De_Ordenes.Services.RabbitMQ;

import com.JSCode.Gestion_De_Ordenes.config.RabbitMQConfig;
import com.JSCode.Gestion_De_Ordenes.dto.rabbitMQ.pedidoDTO;
import com.JSCode.Gestion_De_Ordenes.services.RabbitMQ.PedidoProducer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;


public class PedidoProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private PedidoProducer pedidoProducer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEnviarPedido() {
        // Arrange
        pedidoDTO pedido = new pedidoDTO();
        pedido.setId(123L);
        pedido.setShippingAddress("Calle 123, Medellín");

        // Act
        pedidoProducer.enviarPedido(pedido);

        // Assert
        verify(rabbitTemplate, times(1)).convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                pedido
        );
    }
}
