package web.app.engrivals.engrivals.persistance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class UserEntity {
  @Id
  @UuidGenerator
  @Column(name = "id_user")
  private String id;
  private String name;
  private String profile_url;
  private String email;
  @JsonIgnore
  private String password;
  @Column(columnDefinition = "DATE")
  private LocalDate birthdate;
  @Column(columnDefinition = "DATETIME")
  private LocalDate creation_date;
  private int score;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getProfile_url() {
    return profile_url;
  }

  public void setProfile_url(String profile_url) {
    this.profile_url = profile_url;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LocalDate getBirthdate() {
    return birthdate;
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

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
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