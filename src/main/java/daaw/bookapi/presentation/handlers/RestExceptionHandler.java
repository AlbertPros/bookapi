package daaw.bookapi.presentation.handlers;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException; //no esta en la guia
import org.springframework.web.bind.annotation.ExceptionHandler; 
import org.springframework.web.bind.annotation.RestControllerAdvice;

import daaw.bookapi.application.exceptions.BookNotFoundException;
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String>handleBookNotFound(BookNotFoundException ex) {
        String message = ex.getMessage() != null ? ex.getMessage() :
        "Book not found";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getField() + ": " +
        error.getDefaultMessage())
        .collect(Collectors.joining("; "));
        return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(message);
    }
} 
