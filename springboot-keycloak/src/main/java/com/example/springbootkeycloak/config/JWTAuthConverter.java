package com.example.springbootkeycloak.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class JWTAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
  @Override
  public AbstractAuthenticationToken convert(Jwt source) {
    Collection<GrantedAuthority> roles= extractAuthorities(source);
    return new JwtAuthenticationToken(source,roles);
  }

  //Extracting roles from the JWT token
  private Collection<GrantedAuthority> extractAuthorities(Jwt jwt){
    if(jwt.getClaims() != null){

      Map<String,Object> realmAccess = jwt.getClaim("realm_access");
      ObjectMapper mapper = new ObjectMapper();
      List<String> keyCloakRoles = mapper.convertValue(realmAccess.get("roles"),List.class);
      List<GrantedAuthority> roles = new ArrayList<>();

      for( String keyCloakRole : keyCloakRoles){
        roles.add(new SimpleGrantedAuthority("ROLE_" + keyCloakRole));
      }
      return roles;
    }

    return new ArrayList<>();
  }
}
