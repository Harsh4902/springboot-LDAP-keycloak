<span align="center">
 <h1>SSO using springboot-LDAP-keycloak</h1>
</span>

## Description
- This project is combination of two project: <br>  
  1. SpringBoot-keycloak
  2. SpringBoot-LDAP
- Purpose of SpringBoot-keycloak is to provide centralized system where users can login using their credential in order to use SSO for rest of the systems.
- Purpose of SpringBoot-LDAP is to provide interface to admins to add users in LDAP server which can further used by keycloak to provide authentication and token.

## Features
1. SpringBoot-keycloak
   - Facility of Single Sign On(SSO) with the help of Keyclaok.
   - Uses its own Keycloak authenticator so we can take leverage of Spring session and don't have to call keycloak every time.
   - Provides custom login page so dont have to use keycloak's login page.

2. SpringBoot-LDAP
   - Provides interfacing for perform CRUD operations in LDAP server.
   - Provides support for both embedded as well as external LDAP server.
   - End users can customize configuration of LDAP server based on their requirements.
   - User can read project's readme for customize the LDAP configuration [here](./springboot-LDAP/README.md)

## How to Run Project
1. Install
   ```
   git clone https://github.com/Harsh4902/springboot-LDAP-keycloak.git

   ```
2. Run Tests of Springboot-Keyclaok
   ```
   cd springboot-keycloak
   .\mvnw test
   ```
3. Run Tests of Springboot-LDAP
   ```
   cd springboot-LDAP
   .\mvnw test
   ```


   
