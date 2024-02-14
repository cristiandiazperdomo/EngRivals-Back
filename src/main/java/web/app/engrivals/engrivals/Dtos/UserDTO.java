package web.app.engrivals.engrivals.Dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    @NotNull(message = "Name cannot be empty")
    private String name;
    private String profile_url;
    @NotNull(message = "email cannot be empty")
    @Email(message = "email format is invalid")
    private String email;
    @NotNull(message = "password cannot be empty")
    private String password;
    @NotNull(message = "birthdate cannot be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    private LocalDate creation_date;
    private int score;
    @NotNull(message = "level cannot be empty")
    private Integer level_id_level;
}
