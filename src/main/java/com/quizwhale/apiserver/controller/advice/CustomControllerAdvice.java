package com.quizwhale.apiserver.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.quizwhale.apiserver.util.CustomJWTException;
import com.quizwhale.apiserver.util.CustomServiceException;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> notExist(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("msg", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleIllegalArgumentException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("msg", e.getMessage()));
    }

    @ExceptionHandler(CustomJWTException.class)
    protected ResponseEntity<?> handleJWTException(CustomJWTException e) {
        String msg = e.getMessage();
        return ResponseEntity.ok().body(Map.of("error", msg));
    }

    @ExceptionHandler(CustomServiceException.class)
    protected ResponseEntity<?> handleMemberServiceException(CustomServiceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
    }

}
