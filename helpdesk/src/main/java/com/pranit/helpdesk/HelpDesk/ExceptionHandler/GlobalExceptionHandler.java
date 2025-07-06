package com.pranit.helpdesk.HelpDesk.ExceptionHandler;

import com.pranit.helpdesk.HelpDesk.Model.ApiResponse;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException ex){
        ApiResponse response = new ApiResponse(ex.getMessage(), false, LocalDateTime.now(),404);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgument(IllegalArgumentException ex){
        ApiResponse response = new ApiResponse(ex.getMessage(), false, LocalDateTime.now(),400);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
