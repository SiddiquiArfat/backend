package com.revly.Revly.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> methodArgumentNotValid(Exception exception, WebRequest wr){
        ErrorDetails ed = new ErrorDetails();
        ed.setDescritpion(wr.getDescription(false));
        ed.setMessage(exception.getMessage());
        return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> exception(Exception exception, WebRequest wr){
        ErrorDetails ed = new ErrorDetails();
        ed.setDescritpion(wr.getDescription(false));
        ed.setMessage(exception.getMessage());
        return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDetails> noHandlerFoundException(Exception exception, WebRequest wr){
        ErrorDetails ed = new ErrorDetails();
        ed.setDescritpion(wr.getDescription(false));
        ed.setMessage(exception.getMessage());
        return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TutorException.class)
    public ResponseEntity<ErrorDetails> tutorException(TutorException exception, WebRequest wr){
        ErrorDetails ed = new ErrorDetails();
        ed.setDescritpion(wr.getDescription(false));
        ed.setMessage(exception.getMessage());
        return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UsersException.class)
    public ResponseEntity<ErrorDetails> usersRepository(UsersException exception, WebRequest wr){
        ErrorDetails ed = new ErrorDetails();
        ed.setDescritpion(wr.getDescription(false));
        ed.setMessage(exception.getMessage());
        return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);
    }


}
