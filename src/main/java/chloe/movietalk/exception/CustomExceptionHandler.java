package chloe.movietalk.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e, HttpServletRequest request) {
        BaseErrorCode errorCode = e.getErrorCode();
        ErrorReason errorReason = errorCode.getErrorReason();
        ErrorResponse errorResponse = new ErrorResponse(errorReason, request.getRequestURL().toString());

        return ResponseEntity.status(HttpStatus.valueOf(errorReason.getStatus())).body(errorResponse);
    }
}
