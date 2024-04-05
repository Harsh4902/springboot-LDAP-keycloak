package com.example.ldap.service;
import com.example.ldap.entity.LdapUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

import javax.naming.Name;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

  @Mock
  private LdapTemplate ldapTemplate;

  @InjectMocks
  private UserService userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testAddUser() {
    // Prepare
    LdapUser ldapUser = new LdapUser();
    ldapUser.setUserName("testUser");
    ldapUser.setPassword("test");

    // Execute
    userService.addUser(ldapUser);

    // Verify
    verify(ldapTemplate).bind(eq("uid=testUser,ou=people"), isNull(), any());
  }

  @Test
  void testGetAllUsers() {
    // Prepare
    when(ldapTemplate.search(anyString(), anyString(), any(AttributesMapper.class)))
      .thenReturn(Collections.singletonList(new LdapUser()));

    // Execute
    List<LdapUser> users = userService.getAllUsers();

    // Verify
    assertEquals(1, users.size());
    // You can add more assertions based on your requirements
  }

  @Test
  void testGetUserById_UserFound() {
    // Prepare
    when(ldapTemplate.search(anyString(), anyString(), any(AttributesMapper.class)))
      .thenReturn(Collections.singletonList("username"));

    // Execute
    String result = userService.getUserById("userId");

    // Verify
    assertEquals("username", result);
  }

  @Test
  void testGetUserById_UserNotFound() {
    // Prepare
    when(ldapTemplate.search(anyString(), anyString(), any(AttributesMapper.class)))
      .thenReturn(Collections.emptyList());

    // Execute
    String result = userService.getUserById("userId");

    // Verify
    assertEquals(null, result);
  }

  @Test
  void testDeleteUser() {
    // Execute
    userService.deleteUser("userId");

    // Verify
    verify(ldapTemplate).unbind((Name) any());
  }
}