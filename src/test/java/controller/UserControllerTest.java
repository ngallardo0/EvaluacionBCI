package controller;

import org.example.controller.UserController;
import org.example.error.ErrorDetail;
import org.example.model.User;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class UserControllerTest {

  @InjectMocks
  UserController userController;

  @Mock
  UserService userService;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void signUpTest() {
    User user = new User();
    user.setEmail("test@test.com");
    user.setPassword("Test1234");

    when(userService.signUp(user)).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

    ResponseEntity<?> response = userController.signUp(user);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  public void signUpTest_UserServiceReturnsError() {
    User user = new User();
    user.setEmail("test@test.com");
    user.setPassword("Test1234");

    ErrorDetail errorDetail = new ErrorDetail(new Date(), HttpStatus.BAD_REQUEST.value(), "User already exists");
    when(userService.signUp(user)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

    ResponseEntity<?> response = userController.signUp(user);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void loginTest() {
    UUID uuid = UUID.randomUUID();
    User user = new User();
    user.setEmail("test@test.com");
    user.setPassword("Test1234");

    when(userService.login(uuid)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

    ResponseEntity<?> response = userController.login(uuid);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void loginTest_UserNotFound() {
    UUID uuid = UUID.randomUUID();

    when(userService.login(uuid)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    ResponseEntity<?> response = userController.login(uuid);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}