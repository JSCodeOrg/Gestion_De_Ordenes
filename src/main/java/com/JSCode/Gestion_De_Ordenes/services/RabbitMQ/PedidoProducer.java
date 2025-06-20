package com.JSCode.Gestion_De_Ordenes.services.RabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.JSCode.Gestion_De_Ordenes.config.RabbitMQConfig;
import com.JSCode.Gestion_De_Ordenes.dto.rabbitMQ.PedidoDTO;

@Service
public class PedidoProducer {

    private final RabbitTemplate rabbitTemplate;

    public PedidoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarPedido(PedidoDTO pedido) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                pedido);
        System.out.println("Pedido enviado a Rabbit: " + pedido.getId());
    };

}