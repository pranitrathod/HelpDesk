package com.pranit.helpdesk.exception;
import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestControllerAdvice public class GlobalExceptionHandler {
 @ExceptionHandler(ResourceNotFoundException.class) ResponseEntity<ApiError> notFound(ResourceNotFoundException e){return error(HttpStatus.NOT_FOUND,"RESOURCE_NOT_FOUND",e.getMessage(),Map.of());}
 @ExceptionHandler(InvalidTicketStateException.class) ResponseEntity<ApiError> conflict(InvalidTicketStateException e){return error(HttpStatus.CONFLICT,"INVALID_TICKET_STATE",e.getMessage(),Map.of());}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ApiError> invalid(MethodArgumentNotValidException e){Map<String,String> errors=new LinkedHashMap<>();e.getBindingResult().getFieldErrors().forEach(f->errors.put(f.getField(),f.getDefaultMessage()));return error(HttpStatus.BAD_REQUEST,"VALIDATION_FAILED","Request validation failed",errors);}
 private ResponseEntity<ApiError> error(HttpStatus s,String c,String m,Map<String,String> e){return ResponseEntity.status(s).body(new ApiError(s.value(),c,m,e));}
}
