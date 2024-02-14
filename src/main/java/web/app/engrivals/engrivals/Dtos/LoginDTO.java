package web.app.engrivals.engrivals.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {
  @NotNull(message = "email cannot empty")
  @Email
  String email;
  @NotNull(message = "password cannot empty")
  String password;
}
