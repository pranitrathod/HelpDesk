package com.pranit.helpdesk.exception;
import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.time.LocalDateTime; import java.util.Map;
@RestControllerAdvice public class ApiExceptionHandler{
 @ExceptionHandler(NotFoundException.class) ResponseEntity<?> notFound(NotFoundException e){return ResponseEntity.status(404).body(Map.of("timestamp",LocalDateTime.now(),"message",e.getMessage()));}
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<?> bad(IllegalArgumentException e){return ResponseEntity.badRequest().body(Map.of("timestamp",LocalDateTime.now(),"message",e.getMessage()));}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<?> validation(MethodArgumentNotValidException e){return ResponseEntity.badRequest().body(Map.of("timestamp",LocalDateTime.now(),"message","Validation failed"));}
}
