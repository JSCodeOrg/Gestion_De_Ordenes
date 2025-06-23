package com.JSCode.Gestion_De_Ordenes.services;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JSCode.Gestion_De_Ordenes.dto.ordenes.RegistroPagoDTO;
import com.JSCode.Gestion_De_Ordenes.dto.ordenes.actualizarEstadoOrdenDTO;
import com.JSCode.Gestion_De_Ordenes.dto.rabbitMQ.pedidoDTO;
import com.JSCode.Gestion_De_Ordenes.models.Ordenes;
import com.JSCode.Gestion_De_Ordenes.models.RegistroPagos;
import com.JSCode.Gestion_De_Ordenes.repositories.OrdenesRepository;
import com.JSCode.Gestion_De_Ordenes.repositories.RegistroPagosRepository;
import com.JSCode.Gestion_De_Ordenes.services.RabbitMQ.PedidoProducer;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@Service
public class OrdenesService {

    @Autowired
    private OrdenesRepository ordenesRepository;

    @Autowired
    private RegistroPagosRepository rPagosRepository;

    @Autowired
    private PedidoProducer pedidoProducer;

    @Transactional
    public RegistroPagoDTO actualizarEstadoOrden(actualizarEstadoOrdenDTO orden, String authToken) {

        try {
            Long orderId = Long.parseLong(orden.getOrderId());

            Ordenes busqueda_orden = ordenesRepository.findById(orderId)
                    .orElseThrow(() -> new NotFoundException("No se ha encontrado la orden referida"));

            busqueda_orden.setStatus("PAGADA");

            ordenesRepository.save(busqueda_orden);

            RegistroPagos registro_pago = new RegistroPagos();
            registro_pago.setFechaPago(LocalDateTime.now());
            registro_pago.setMercadoPagoOrderId(orden.getOrderId());
            registro_pago.setPaymentId(orden.getPayment_id());
            registro_pago.setOrden(busqueda_orden);

            rPagosRepository.save(registro_pago);

            pedidoDTO pedido_envio = new pedidoDTO();
            pedido_envio.setId(orderId);
            pedido_envio.setShippingAddress(busqueda_orden.getShippingAddress());

            pedidoProducer.enviarPedido(pedido_envio);


            RegistroPagoDTO registro = new RegistroPagoDTO();
            registro.setMercadoPagoOrderId(registro_pago.getMercadoPagoOrderId());
            registro.setPaymentId(registro_pago.getPaymentId());
            registro.setStatus("PAGADA");

            return registro;

        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un error al guardar el nuevo estado de orden." + e.getMessage());
        }

    }

}
