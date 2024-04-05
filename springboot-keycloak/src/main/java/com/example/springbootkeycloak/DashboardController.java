package com.example.springbootkeycloak;

import com.example.springbootkeycloak.authenticator.AuthResponse;
import com.example.springbootkeycloak.authenticator.KeyCloakAuthenticator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@Controller
public class DashboardController {

  //endpoint for serving home page

  @GetMapping("/")
  public String homePage(HttpServletRequest request, Model model) {
    model.addAttribute("token",request.getParameter("access_token"));
    return "index";
  }

  // this endpoint will redirect the request to finance service

  @GetMapping("/finance")
  @ResponseBody
  public String finance(Authentication authentication) {
    Jwt token = (Jwt) authentication.getPrincipal();
    Map<String, Object> customClaims = token.getClaims();
    return "Welcome to the Finance Service " + customClaims.get("preferred_username").toString() + " :)";
  }

  // this endpoint will redirect the request to account service

  @GetMapping("/account")
  @ResponseBody
  public String account(Authentication authentication) {
    Jwt token = (Jwt) authentication.getPrincipal();
    Map<String, Object> customClaims = token.getClaims();
    return "Welcome to the Account Service " + customClaims.get("preferred_username").toString() + " :)";
  }

  @GetMapping("/login")
  public String login(){
    return "login";
  }

  // This api is for receiving authentication service from client or frontend.
  @PostMapping("/authenticate")
  public void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      String token = authenticate(request.getParameter("username"),request.getParameter("password"));
      response.sendRedirect("/?access_token="+token);
    }
    catch (Exception e){
      response.sendRedirect("/login");
    }
  }

  // This method sending request to keycloak to authenticate user and takes token from keycloak
  public String authenticate(String username,String password){

    KeyCloakAuthenticator authenticator = KeyCloakAuthenticator.builder()
      .tokenURL("http://localhost:8083/realms/SpringSSO/protocol/openid-connect/token")
      .clientID("spring-keycloak")
      .clientSecret("UVpm5yTahJ4dEcjt0uvdMcP5voQuLfuR")
      .grantType("password")
      .username(username)
      .password(password)
      .build();

    ResponseEntity<AuthResponse> authResponse = null;

    try {
      authResponse = authenticator.authenticate();
    }catch (Exception e){
      System.err.println(e);
    }
    System.out.println(authResponse);
    if(authResponse.getStatusCode() != HttpStatus.OK)
      throw new BadCredentialsException("Bad Credentials.......");
    return authResponse.getBody().access_token;
  }

}
