package ProgramareWebJava.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleInvalidMovie(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest()
                .body("For the filed called '" + e.getFieldError().getField() +
                        "' , its value is not valid -> (" + e.getFieldError().getRejectedValue() +
                        "). The message sent with the validation is: " + e.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleNotFoundMovie(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}