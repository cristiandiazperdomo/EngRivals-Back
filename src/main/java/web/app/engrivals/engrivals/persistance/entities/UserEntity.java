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
  private Integer numberOfGamesWon;
  private Integer score;
  @ManyToOne
  @JoinColumn(name = "level_id_level")
  private EnglishLevel englishLevel_id_level;

  public UserEntity() {
    this.creation_date = LocalDate.now();
    this.numberOfGamesWon = 0;
    this.score = 0;
  }

    public UserEntity(String id, String name, String profile_url, String email, String password, LocalDate birthdate, LocalDate creation_date, Integer numberOfGamesWon, Integer score, EnglishLevel englishLevel_id_level) {
        this.id = id;
        this.name = name;
        this.profile_url = profile_url;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.creation_date = LocalDate.now();
        this.numberOfGamesWon = 0;
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
  
  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public LocalDate getCreation_date() {
    return creation_date;
  }

  public void setCreation_date(LocalDate creation_date) {
    this.creation_date = creation_date;
  }

    public Integer getNumberOfGamesWon() {
        return numberOfGamesWon;
    }

    public void setNumberOfGamesWon(Integer numberOfGamesWon) {
        this.numberOfGamesWon = numberOfGamesWon;
    }
  
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

  public EnglishLevel getEnglishLevel_id_level() {
    return englishLevel_id_level;
  }

  public void setEnglishLevel_id_level(EnglishLevel englishLevel_id_level) {
    this.englishLevel_id_level = englishLevel_id_level;
  }

    @Override
    public String toString() {
        return "UserEntity{" + "id=" + id + ", name=" + name + ", profile_url=" + profile_url + ", email=" + email + ", password=" + password + ", birthdate=" + birthdate + ", creation_date=" + creation_date + ", score=" + score + ", englishLevel_id_level=" + englishLevel_id_level + '}';
    }
}