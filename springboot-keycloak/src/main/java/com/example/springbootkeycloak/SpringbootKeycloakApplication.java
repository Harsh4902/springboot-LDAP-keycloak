package com.example.springbootkeycloak;

import com.example.springbootkeycloak.authenticator.AuthResponse;
import com.example.springbootkeycloak.authenticator.KeyCloakAuthenticator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;


@SpringBootApplication
public class SpringbootKeycloakApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootKeycloakApplication.class, args);
  }

}
