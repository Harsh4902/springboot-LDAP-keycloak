package com.example.ldap.entity;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DBUserRepositoryTest {

  @Mock
  private DBUserRepository dbUserRepositoryMock;

  @Test
  void testFindByUid() {
    // Prepare
    String uid = "testUid";
    DBUser dbUser = new DBUser();
    dbUser.setUid(uid);
    when(dbUserRepositoryMock.findByUid(uid)).thenReturn(dbUser);

    // Execute
    DBUser foundUser = dbUserRepositoryMock.findByUid(uid);

    // Verify
    assertEquals(uid, foundUser.getUid());
  }
}
