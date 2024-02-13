package web.app.engrivals.engrivals.persistance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class UserEntity implements UserDetails {
  @Id
  @UuidGenerator
  @Column(name = "id_user")
  private String id;
  private String name;
  private String profile_url;
  private String email;
  @Enumerated(EnumType.STRING)
  private Role role;
  @JsonIgnore
  private String password;
  @Column(columnDefinition = "DATE")
  private LocalDate birthdate;
  @Column(columnDefinition = "DATETIME")
  private LocalDate creation_date;
  private Integer score;
  @ManyToOne
  @JoinColumn(name = "level_id_level")
  private EnglishLevel englishLevel_id_level;

  public UserEntity() {
    this.creation_date = LocalDate.now();
    this.score = 0;
  }

  public UserEntity(String id, String name, String profile_url, String email, String password, LocalDate birthdate, EnglishLevel englishLevel_id_level) {
    this.id = id;
    this.name = name;
    this.profile_url = profile_url;
    this.email = email;
    this.password = password;
    this.birthdate = birthdate;
    this.creation_date = LocalDate.now();
    this.score = 0;
    this.englishLevel_id_level = englishLevel_id_level;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }
  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}