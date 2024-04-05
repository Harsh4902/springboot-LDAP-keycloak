package com.example.springbootkeycloak.authenticator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KeyCloakAuthenticatorTest {

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private KeyCloakAuthenticator keyCloakAuthenticator;

  @Test
  public void testAuthenticate() throws URISyntaxException {
    // Given
    String clientId = "client-id";
    String clientSecret = "client-secret";
    String grantType = "password";
    String username = "testuser";
    String password = "testpassword";

    keyCloakAuthenticator.tokenURL("http://localhost:8083/realms/SpringSSO/protocol/openid-connect/token")
      .clientID(clientId)
      .clientSecret(clientSecret)
      .grantType(grantType)
      .username(username)
      .password(password);

    AuthResponse expectedResponse = new AuthResponse();
    // Set up your expected response here

    // Mock restTemplate behavior
    when(restTemplate.postForObject(any(URI.class), any(HttpEntity.class), eq(AuthResponse.class)))
      .thenReturn(expectedResponse);

    // When
    ResponseEntity<AuthResponse> responseEntity = keyCloakAuthenticator.authenticate();

    // Then
    verify(restTemplate, times(1)).postForObject(any(), any(), eq(AuthResponse.class));
    // Add more assertions as needed
  }
}
