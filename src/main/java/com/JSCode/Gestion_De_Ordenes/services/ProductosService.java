package com.JSCode.Gestion_De_Ordenes.services;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.JSCode.Gestion_De_Ordenes.dto.compras.productos.productoCantidadDTO;

@Service
public class ProductosService {

    private final String inventarioUrl = "http://localhost:8080/inventario/existencias";

    public Boolean verificarExistencias(List<productoCantidadDTO> productos) {

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<List<productoCantidadDTO>> request = new HttpEntity<>(productos);
        ResponseEntity<Boolean> response = restTemplate.exchange(
                inventarioUrl,
                HttpMethod.POST,
                request,
                Boolean.class);
        return response.getBody();
    }

}
