package com.JSCode.Gestion_De_Ordenes.Services;

import com.JSCode.Gestion_De_Ordenes.dto.ordenes.AddressDTO;
import com.JSCode.Gestion_De_Ordenes.security.JwtUtil;
import com.JSCode.Gestion_De_Ordenes.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetShippingAddress_Exito() {
        // Arrange
        String token = "Bearer abc.def.ghi";
        String cleanToken = "abc.def.ghi";
        String userId = "123";
        String url = "http://api-gateway:8080/usuarios/users/getaddress/123";

        AddressDTO expectedAddress = new AddressDTO();
        expectedAddress.setCiudad("Bogotá");
        expectedAddress.setDireccion("Calle 123");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        when(jwtUtil.extractUsername(cleanToken)).thenReturn(userId);
        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(AddressDTO.class)))
                .thenReturn(ResponseEntity.ok(expectedAddress));

        // Act
        AddressDTO result = userService.getShippingAddress(token);

        // Assert
        assertNotNull(result);
        assertEquals("Bogotá", result.getCiudad());
        assertEquals("Calle 123", result.getDireccion());
    }

    @Test
    public void testGetShippingAddress_Error() {
        // Arrange
        String token = "Bearer abc.def.ghi";
        String cleanToken = "abc.def.ghi";
        String userId = "123";
        String url = "http://api-gateway:8080/usuarios/users/getaddress/123";

        when(jwtUtil.extractUsername(cleanToken)).thenReturn(userId);
        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(AddressDTO.class)))
                .thenThrow(new RuntimeException("Fallo"));

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            userService.getShippingAddress(token);
        });

        assertTrue(ex.getMessage().contains("No se pudo obtener la dirección"));
    }
}
