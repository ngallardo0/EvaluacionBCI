package org.example.service;


import org.example.error.ErrorDetail;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private static final String KEY_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d.*\\d)[a-zA-Z\\d]{8,12}$";
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
    }

    public boolean isValidEmail(String email) {
      Pattern pattern = Pattern.compile(KEY_REGEX);
      Matcher matcher = pattern.matcher(email);
      return matcher.matches();
    }

  public boolean isValidPassword(String password) {
    Pattern pattern = Pattern.compile(PASSWORD_REGEX);
    Matcher matcher = pattern.matcher(password);
    return matcher.matches();
  }

    public ResponseEntity<?> signUp(User user) {

      if (user.getEmail() == null || user.getEmail().isEmpty()) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), HttpStatus.BAD_REQUEST.value(), "Email is required");
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
      }
      if (!isValidEmail(user.getEmail())) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), HttpStatus.BAD_REQUEST.value(), "Invalid email format");
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
      }

      if (user.getPassword() == null || user.getPassword().isEmpty()) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), HttpStatus.BAD_REQUEST.value(), "Password is required");
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
      }
      if (!isValidPassword(user.getPassword())) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), HttpStatus.BAD_REQUEST.value(), "Invalid password format");
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
      }

      // Check if the user already exists
      User existingUser = userRepository.findByEmail(user.getEmail());
      if (existingUser != null) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), HttpStatus.BAD_REQUEST.value(), "User already exists");
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);

      }

      user.setActive(Boolean.TRUE);
      user.setToken(user.getId());
      user.setPhones(user.getPhones());
      userRepository.save(user);

      return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

  public ResponseEntity<?> login(UUID uuid) {

      Optional<User> userOptional = userRepository.findById(uuid);
      if (userOptional.isPresent()) {
        User user = userOptional.get();
        user.setLastLogin(new Date());
        user.setToken(UUID.randomUUID());
        return new ResponseEntity<>(user, HttpStatus.OK);
      } else {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
      }
    }

}





