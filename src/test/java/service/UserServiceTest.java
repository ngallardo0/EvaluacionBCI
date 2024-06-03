package service;

import org.example.error.ErrorDetail;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class UserServiceTest {

  @InjectMocks
  UserService userService;

  @Mock
  UserRepository userRepository;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void signUpTest() {
    User user = new User();
    user.setEmail("test@test.com");
    user.setPassword("Test1234");

    when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

    ResponseEntity<?> response = userService.signUp(user);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  public void signUpTest_UserAlreadyExists() {
    User user = new User();
    user.setEmail("test@test.com");
    user.setPassword("Test1234");

    when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

    ResponseEntity<?> response = userService.signUp(user);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(response.getBody() instanceof ErrorDetail);
    assertEquals("User already exists", ((ErrorDetail) response.getBody()).getDetail());
  }


  @Test
  public void signUpTest_NullEmail() {
    User user = new User();
    user.setPassword("Test1234");

    ResponseEntity<?> response = userService.signUp(user);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(response.getBody() instanceof ErrorDetail);
    assertEquals("Email is required", ((ErrorDetail) response.getBody()).getDetail());
  }

  @Test
  public void signUpTest_InvalidEmail() {
    User user = new User();
    user.setEmail("invalidEmail");
    user.setPassword("Test1234");

    ResponseEntity<?> response = userService.signUp(user);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(response.getBody() instanceof ErrorDetail);
    assertEquals("Invalid email format", ((ErrorDetail) response.getBody()).getDetail());
  }

  @Test
  public void signUpTest_NullPassword() {
    User user = new User();
    user.setEmail("test@test.com");

    ResponseEntity<?> response = userService.signUp(user);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(response.getBody() instanceof ErrorDetail);
    assertEquals("Password is required", ((ErrorDetail) response.getBody()).getDetail());
  }

  @Test
  public void signUpTest_InvalidPassword() {
    User user = new User();
    user.setEmail("test@test.com");
    user.setPassword("invalidPassword");

    ResponseEntity<?> response = userService.signUp(user);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(response.getBody() instanceof ErrorDetail);
    assertEquals("Invalid password format", ((ErrorDetail) response.getBody()).getDetail());
  }


  @Test
  public void loginTest_UserNotFound() {
    UUID uuid = UUID.randomUUID();

    when(userRepository.findById(uuid)).thenReturn(Optional.empty());

    ResponseEntity<?> response = userService.login(uuid);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("User not found", response.getBody());
  }
}