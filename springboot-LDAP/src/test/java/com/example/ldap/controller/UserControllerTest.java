package com.example.ldap.controller;

import com.example.ldap.entity.LdapUser;
import com.example.ldap.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testAddUser() {
    // Prepare
    LdapUser user = new LdapUser();

    // Execute
    ResponseEntity<HttpStatus> responseEntity = userController.addUser(user);

    // Verify
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    verify(userService, times(1)).addUser(user);
  }

  @Test
  void testGetAllUser() {
    // Prepare
    List<LdapUser> userList = new ArrayList<>();
    when(userService.getAllUsers()).thenReturn(userList);

    // Execute
    ResponseEntity<List<LdapUser>> responseEntity = userController.getALlUser();

    // Verify
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(userList, responseEntity.getBody());
  }

  @Test
  void testHello() {
    // Execute
    String result = userController.hello();

    // Verify
    assertEquals("Hello User", result);
  }

  @Test
  void testRemoveUser() {
    // Prepare
    String uid = "testUid";

    // Execute
    ResponseEntity<HttpStatus> responseEntity = userController.removeUser(uid);

    // Verify
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    verify(userService, times(1)).deleteUser(uid);
  }
}
