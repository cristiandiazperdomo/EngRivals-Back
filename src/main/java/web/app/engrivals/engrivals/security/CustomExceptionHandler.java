package web.app.engrivals.engrivals.security;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidateException(MethodArgumentNotValidException e) {
    Map<String, String> errors = e.getBindingResult()
            .getAllErrors()
            .stream()
            .collect(Collectors.toMap(
                    err -> "message",
                    err -> {
                      String message = err.getDefaultMessage();
                      return message != null ? message : "not message";
                    }
            ));
    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Object> handleDateFormatException(HttpMessageNotReadableException e) {
    Map<String, String> except = new HashMap<>() {{
      put("message", "Time format invalid, yyyy-MM-dd");
    }};
    return ResponseEntity.badRequest().body(except);
  }
}
