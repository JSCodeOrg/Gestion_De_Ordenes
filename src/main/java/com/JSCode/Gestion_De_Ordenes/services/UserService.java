package com.JSCode.Gestion_De_Ordenes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.JSCode.Gestion_De_Ordenes.dto.ordenes.AddressDTO;
import com.JSCode.Gestion_De_Ordenes.security.JwtUtil;

@Service
public class UserService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RestTemplate restTemplate; // ✅ ahora es inyectable y testeable

    private final String userMicroserviceUrl = "http://api-gateway:8080/usuarios/users/getaddress";

    public AddressDTO getShippingAddress(String authToken) {

        String token = authToken.substring(7);
        String userId = jwtUtil.extractUsername(token);

        try {
            System.out.println(authToken);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authToken);

            HttpEntity<Void> request = new HttpEntity<>(headers);

            String url = userMicroserviceUrl + "/" + userId;

            ResponseEntity<AddressDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    AddressDTO.class
            );

            return response.getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("No se pudo obtener la dirección de envío del usuario", e);
        }
    }
}
