package com.JSCode.Gestion_De_Ordenes.services;

import org.springframework.stereotype.Service;
import com.JSCode.Gestion_De_Ordenes.dto.compras.productos.productoDTO;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.client.preference.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Service
public class MercadoPagoService {

    @Value("${mercadopago.access-token}")
    private String accessToken;

    private final String frontend_url = "https://9b65-2800-484-b179-a510-2c-36ca-40d8-ce78.ngrok-free.app";

    @Autowired
    private ProductosService productosService;

    public String crearPreferenciaPago(List<productoDTO> productos, Long orden_id) throws MPException {
        try {
            MercadoPagoConfig.setAccessToken(accessToken);

            List<PreferenceItemRequest> items = new ArrayList<>();

            for (productoDTO producto : productos) {
                PreferenceItemRequest item = PreferenceItemRequest.builder()
                        .title(producto.getNombre())
                        .quantity(producto.getCantidad())
                        .unitPrice(producto.getPrecioUnitario())
                        .currencyId("COP")
                        .build();
                items.add(item);
            }

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success(frontend_url + "/?orderId=" + orden_id)
                    .failure(frontend_url + "/?orderId=" + orden_id)
                    .pending(frontend_url + "/?orderId=" + orden_id)
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .autoReturn("approved")
                    .build();

            PreferenceClient client = getPreferenceClient();
            Preference preference = client.create(preferenceRequest);

            return preference.getInitPoint();

        } catch (MPApiException e) {
            System.out.println("🛑 Error al crear preferencia de pago:");
            System.out.println("Código de estado: " + e.getStatusCode());
            System.out.println("Respuesta completa: " + e.getApiResponse().getContent());
            throw new RuntimeException("No se pudo crear la preferencia de pago: " + e.getMessage(), e);

        } catch (MPException e) {
            e.printStackTrace();
            throw new RuntimeException("Error interno de MercadoPago SDK: " + e.getMessage(), e);
        }
    }

    /**
     * Método protegido para permitir sobreescribir la creación del PreferenceClient en pruebas.
     */
    public PreferenceClient getPreferenceClient() {
        return new PreferenceClient();
    }
}
