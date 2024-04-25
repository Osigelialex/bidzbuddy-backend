package com.example.biddingsystem.exceptions;

import com.example.biddingsystem.utils.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ErrorDetails> handleBadRequestException(ValidationException authBadRequestException) {
        ErrorDetails errorDetails = new ErrorDetails(authBadRequestException.getMessage(),
                HttpStatus.BAD_REQUEST, new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    @ExceptionHandler(value = DataConflictException.class)
    public ResponseEntity<ErrorDetails> handleEmailExistsException(DataConflictException dataConflictException) {
        ErrorDetails errorDetails = new ErrorDetails(dataConflictException.getMessage(),
                HttpStatus.CONFLICT, new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = BiddingUnauthorizedException.class)
    public ResponseEntity<ErrorDetails> handleBiddingUnauthorizedException(
            BiddingUnauthorizedException biddingUnauthorizedException) {
        ErrorDetails errorDetails = new ErrorDetails(biddingUnauthorizedException.getMessage(),
                HttpStatus.UNAUTHORIZED, new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = MediaUploadException.class)
    public ResponseEntity<ErrorDetails> handleMediaUploadException(MediaUploadException mediaUploadException) {
        ErrorDetails errorDetails = new ErrorDetails(mediaUploadException.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR, new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        ErrorDetails errorDetails = new ErrorDetails(resourceNotFoundException.getMessage(),
                HttpStatus.NOT_FOUND, new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

}

