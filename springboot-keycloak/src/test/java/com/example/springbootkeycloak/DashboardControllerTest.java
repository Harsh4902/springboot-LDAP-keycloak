package com.example.springbootkeycloak;

import com.example.springbootkeycloak.authenticator.AuthResponse;
import com.example.springbootkeycloak.authenticator.KeyCloakAuthenticator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DashboardControllerTest {

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private KeyCloakAuthenticator keyCloakAuthenticator;

  @InjectMocks
  private DashboardController dashboardController;

  @Test
  public void testAuthenticate_Success() throws IOException, URISyntaxException {
    // Given
    String username = "testUser";
    String password = "testPassword";
    String accessToken = "testAccessToken";
    ResponseEntity<AuthResponse> authResponseEntity = ResponseEntity.ok(new AuthResponse());

    when(request.getParameter("username")).thenReturn(username);
    when(request.getParameter("password")).thenReturn(password);
    when(keyCloakAuthenticator.authenticate()).thenReturn(authResponseEntity);

    // When
    dashboardController.authenticate(request, response);

    // Then
    verify(response).sendRedirect("/?access_token=" + accessToken);
  }

  @Test
  public void testAuthenticate_Failure() throws IOException, URISyntaxException {
    // Given
    String username = "testUser";
    String password = "testPassword";
    ResponseEntity<AuthResponse> authResponseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    when(request.getParameter("username")).thenReturn(username);
    when(request.getParameter("password")).thenReturn(password);
    when(keyCloakAuthenticator.authenticate()).thenReturn(authResponseEntity);

    // When
    dashboardController.authenticate(request, response);

    // Then
    verify(response).sendRedirect("/login");
  }
}
