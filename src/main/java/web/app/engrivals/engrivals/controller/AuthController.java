package web.app.engrivals.engrivals.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.app.engrivals.engrivals.Dtos.LoginDTO;
import web.app.engrivals.engrivals.Dtos.UserDTO;
import web.app.engrivals.engrivals.persistance.entities.AuthResponse;
import web.app.engrivals.engrivals.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserDTO request){
    return ResponseEntity.ok(authService.register(request));
  }
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDTO request){
    return ResponseEntity.ok(authService.login(request));
  }
}
