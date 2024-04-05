package com.example.ldap.config;

import com.example.ldap.entity.DBUser;
import com.example.ldap.entity.DBUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.config.ldap.LdapPasswordComparisonAuthenticationManagerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.server.UnboundIdContainer;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private DBUserRepository dbUserRepository;

  @Bean
  public EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean() {
    EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean =
      EmbeddedLdapServerContextSourceFactoryBean.fromEmbeddedLdapServer();
    contextSourceFactoryBean.setPort(0);
    return contextSourceFactoryBean;
  }

  //This is bean is for connecting to LDAP server on remote location

//  @Bean
//  public ContextSource contextSource(){
//    LdapContextSource ldapContextSource = new LdapContextSource() ;
//    ldapContextSource.setUrl("ldap://192.168.12.64:389");
//    ldapContextSource.setBase("dc=example,dc=com");
//    ldapContextSource.setUserDn("cn=admin,dc=example,dc=com");
//    ldapContextSource.setPassword("sttl@321");
//    return ldapContextSource;
//  }


  //This bean is for spring's embedded LDAP server
  @Bean
  UnboundIdContainer ldapContainer() {
    return new UnboundIdContainer("dc=springframework,dc=org",
      "classpath:users.ldif");
  }

  @Bean
  LdapAuthoritiesPopulator authorities(BaseLdapPathContextSource contextSource) {
    String groupSearchBase = "ou=groups";
    DefaultLdapAuthoritiesPopulator authorities = new DefaultLdapAuthoritiesPopulator
      (contextSource, groupSearchBase);
    authorities.setGroupSearchFilter("(member={0})");
    return authorities;
  }

  // This bean is for creating authentication manager which will authenticate user using LDAP's username & password

  @Bean
  AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource,
                                              LdapAuthoritiesPopulator authorities) {
    LdapPasswordComparisonAuthenticationManagerFactory factory = new LdapPasswordComparisonAuthenticationManagerFactory(contextSource,new BCryptPasswordEncoder());
    factory.setUserDnPatterns("uid={0}");
    factory.setPasswordAttribute("userPassword");
    return factory.createAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
      .csrf(t -> t.disable())
      .authorizeHttpRequests(t -> {
        t
          .requestMatchers("/user/**").permitAll()
          .anyRequest().authenticated();
      })
      .formLogin(t -> {
        t
          .successHandler((request, response, authentication) -> {
          System.out.println(authentication.getPrincipal());
          LdapUserDetailsImpl ldapUserDetails =(LdapUserDetailsImpl) authentication.getPrincipal();
          String uid = ldapUserDetails.getDn().split(",")[0].split("=")[1];

          //If current LDAP use is not present in db then will add it.
          if(dbUserRepository.findByUid(uid) == null){
            DBUser user = new DBUser();
            user.setUid(uid);
            dbUserRepository.save(user);
          }
          response.sendRedirect("/");
        });
      });
    return httpSecurity.build();
  }
}
