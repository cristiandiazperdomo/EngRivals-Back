package web.app.engrivals.engrivals.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.app.engrivals.engrivals.Dtos.LoginDTO;
import web.app.engrivals.engrivals.Dtos.UserDTO;
import web.app.engrivals.engrivals.persistance.entities.AuthResponse;
import web.app.engrivals.engrivals.persistance.entities.EnglishLevel;
import web.app.engrivals.engrivals.persistance.entities.Role;
import web.app.engrivals.engrivals.persistance.entities.UserEntity;
import web.app.engrivals.engrivals.persistance.repository.LevelRepository;
import web.app.engrivals.engrivals.persistance.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
  private final PasswordEncoder passwordEncoder;
  private final LevelRepository levelRepository;
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Transactional
  public AuthResponse register(UserDTO request) {
    boolean isUserRegistered = userRepository.existsByEmail(request.getEmail());
    if(isUserRegistered) return null;

    Optional<EnglishLevel> level = levelRepository.findById(request.getLevel_id_level());
    if (level.isPresent()) {
      UserEntity user = UserEntity.builder()
              .name(request.getName())
              .profile_url(request.getProfile_url())
              .email(request.getEmail())
              .role(Role.USER)
              .password(passwordEncoder.encode(request.getPassword()))
              .birthdate(request.getBirthdate())
              .creation_date(LocalDate.now())
              .score(0)
              .englishLevel_id_level(level.get())
              .build();

      userRepository.save(user);
      return new AuthResponse(jwtService.generateToken(user));
    }
    return null;
  }

  public AuthResponse login(LoginDTO request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    UserDetails user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    String token = jwtService.generateToken(user);

    return new AuthResponse(token);
  }
}
