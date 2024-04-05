package com.example.springbootkeycloak.authenticator;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/*
* Helper class for sending request to get token to keycloak.
*/

@Component
public class KeyCloakAuthenticator {

  private String tokenURL;
  private String clientId;
  private String clientSecret;
  private String grantType;
  private String username;

  public String getTokenURL() {
    return tokenURL;
  }

  public String getClientId() {
    return clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public String getGrantType() {
    return grantType;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  private String password;

  public KeyCloakAuthenticator tokenURL(String val){
    this.tokenURL = val;
    return this;
  }

  public KeyCloakAuthenticator clientID(String val){
    this.clientId = val;
    return this;
  }

  public KeyCloakAuthenticator clientSecret(String val){
    this.clientSecret = val;
    return this;
  }

  public KeyCloakAuthenticator grantType(String val){
    this.grantType = val;
    return this;
  }

  public KeyCloakAuthenticator username(String val){
    this.username = val;
    return this;
  }

  public KeyCloakAuthenticator password(String val){
    this.password = val;
    return this;
  }

  public static KeyCloakAuthenticator builder(){
    return new KeyCloakAuthenticator();
  }

  public KeyCloakAuthenticator build(){
    return this;
  }
  public ResponseEntity<AuthResponse> authenticate() throws URISyntaxException {

    RestClient restClient = RestClient.builder().build();

    RestTemplate restTemplate = new RestTemplate();

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("client_id",clientId);
    map.add("client_secret",clientSecret);
    map.add("grant_type",grantType);
    map.add("username",username);
    map.add("password",password);

    URI uri = new URI(tokenURL);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    AuthResponse authResponse = restTemplate.postForObject(uri,new HttpEntity<>(map,headers), AuthResponse.class);
    System.out.println(authResponse);
    return new ResponseEntity<>(authResponse, HttpStatus.OK);
  }

}
