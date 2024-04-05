package com.example.springbootkeycloak.authenticator;

/*
* DTO for receiving response from keycloak and sending to clients.
*/

public class AuthResponse {

  public String access_token;
  public int expires_in;
  public int refresh_expires_in;
  public String refresh_token;
  public String token_type;
  public int not_before_policy;
  public String session_state;

  @Override
  public String toString() {
    return "AuthResponse{" +
      "access_token='" + access_token + '\'' +
      ", expires_in=" + expires_in +
      ", refresh_expires_in=" + refresh_expires_in +
      ", refresh_token='" + refresh_token + '\'' +
      ", token_type='" + token_type + '\'' +
      ", not_before_policy=" + not_before_policy +
      ", session_state='" + session_state + '\'' +
      ", scope='" + scope + '\'' +
      '}';
  }

  public String scope;

}
