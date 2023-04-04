package com.example.restservice.exception;

import com.example.restservice.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

//에러를 핸들링 하기 위해 스프링부트에서 제공하는 클래스
//모든 컨트롤러가 실행이 될 때 @ControllerAdvice 예외를 전담해서 처리
@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    //모든 예외처리를 처리해 주는 메서드
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handeAllException(Exception ex,
                                                          WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //UserNotFoundException.class => handleUserNotFoundException()
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity handleUserNotFoundException(Exception ex,
                                                            WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), "Validation Failed",
                        ex.getBindingResult().toString());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
