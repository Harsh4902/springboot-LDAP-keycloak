package com.example.springbootkeycloak.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;


/*
  - Config file for security of this application.
  - Override or rewrite it based on your requirement

  Author @Harshvardhan_Parmar
*/
@EnableWebSecurity
@Configuration
public class SecurityConfig {

  @Autowired
  private JWTAuthConverter jwtAuthConverter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(t -> {
        t
          .requestMatchers("/login","/authenticate").permitAll()
          .requestMatchers("/").hasRole("default-roles-springsso")
          .anyRequest().authenticated();
      })
      .oauth2ResourceServer(t -> {
        t.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthConverter));
      })
      .sessionManagement(
        t -> t.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
      );


    return httpSecurity.build();
  }
  @Bean
  public BearerTokenResolver bearerTokenResolver() {
    return new CustomTokenResolver();
  }

}
