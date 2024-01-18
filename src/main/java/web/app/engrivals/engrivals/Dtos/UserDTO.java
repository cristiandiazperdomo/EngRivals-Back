package web.app.engrivals.engrivals.Dtos;

import java.time.LocalDate;
import web.app.engrivals.engrivals.persistance.entities.EnglishLevel;

public class UserDTO {
    private String id;
    private String name;
    private String profile_url;
    private String email;
    private String password;
    private LocalDate birthdate;
    private LocalDate creation_date;
    private int score;
    private EnglishLevel level_id_level;

    public UserDTO() {
    }

    public UserDTO(String id, String name, String profile_url, String email, String password, LocalDate birthdate, LocalDate creation_date, int score, EnglishLevel level_id_level) {
        this.id = id;
        this.name = name;
        this.profile_url = profile_url;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.creation_date = creation_date;
        this.score = score;
        this.level_id_level = level_id_level;
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

    public EnglishLevel getLevel_id_level() {
        return level_id_level;
    }

    public void setLevel_id_level(EnglishLevel level_id_level) {
        this.level_id_level = level_id_level;
    }
    
    
}
