package org.example.controller;


import org.example.model.User;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(UserController.URI)
public class UserController {
  public static final String URI = "api/user";
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody User user) {
    return userService.signUp(user);
  }

  @GetMapping("login/{uuid}")
  public ResponseEntity<?> login(@PathVariable("uuid") UUID uuid) {
    return userService.login(uuid);
  }
}
