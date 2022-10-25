package com.challenge.secretnotepad.rest;

import com.challenge.secretnotepad.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Handles the exceptions thrown anywhere in application.
 * <p>
 * @author Mohamed Amine Allani
 */
@ControllerAdvice
public class SecretNoteControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecretNoteControllerAdvice.class);

    /**
     * Handles POST Request body validation failures.
     * @param ex handled exception
     * @param headers request headers
     * @param status response status
     * @param request request sent
     * @return ResponseEntity with error type NOT_FOUND
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        LOGGER.error("Body request validation failed", ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("timestamp", LocalDateTime.now());

        if (ex.getBindingResult().getFieldError() != null) {
            body.put("message", ex.getBindingResult().getFieldError().getDefaultMessage());
        } else {
            body.put("message", "Body request validation failed");
        }

        return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Handles all {@link SecretNoteNotFoundException SecretNoteNotFoundExceptions} by returning a {@link HttpStatus#NOT_FOUND}
     * @param ex handled exception
     * @param request request sent
     * @return ResponseEntity with error type NOT_FOUND
     */
    @ExceptionHandler(SecretNoteNotFoundException.class)
    public ResponseEntity<Object> handle(SecretNoteNotFoundException ex, WebRequest request) {

        LOGGER.error("SecretNoteNotFoundException was thrown", ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all {@link MalformedIdException MalformedIdExceptions} by returning a {@link HttpStatus#BAD_REQUEST}
     * @param ex handled exception
     * @param request request sent
     * @return ResponseEntity with error type BAD_REQUEST
     */
    @ExceptionHandler(MalformedIdException.class)
    public ResponseEntity<Object> handle(MalformedIdException ex, WebRequest request) {

        LOGGER.error("MalformedIdException was thrown", ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all {@link TitleAlreadyExistsException TitleAlreadyExistsExceptions} by returning a {@link HttpStatus#BAD_REQUEST}
     * @param ex handled exception
     * @param request request sent
     * @return ResponseEntity with error type BAD_REQUEST
     */
    @ExceptionHandler(TitleAlreadyExistsException.class)
    public ResponseEntity<Object> handle(TitleAlreadyExistsException ex, WebRequest request) {

        LOGGER.error("TitleAlreadyExistsException was thrown", ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all {@link IllegalStateException IllegalStateExceptions} by returning a {@link HttpStatus#INTERNAL_SERVER_ERROR}
     * @param ex handled exception
     * @param request request sent
     * @return ResponseEntity with error type INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handle(IllegalStateException ex, WebRequest request) {

        LOGGER.error("IllegalStateException was thrown", ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles all {@link EncryptionException EncryptionExceptions} by returning a {@link HttpStatus#INTERNAL_SERVER_ERROR}
     * @param ex handled exception
     * @param request request sent
     * @return ResponseEntity with error type INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(EncryptionException.class)
    public ResponseEntity<Object> handle(EncryptionException ex, WebRequest request) {

        LOGGER.error("EncryptionException was thrown", ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Handles all {@link DecryptionException DecryptionExceptions} by returning a {@link HttpStatus#INTERNAL_SERVER_ERROR}
     * @param ex handled exception
     * @param request request sent
     * @return ResponseEntity with error type INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(DecryptionException.class)
    public ResponseEntity<Object> handle(DecryptionException ex, WebRequest request) {

        LOGGER.error("DecryptionException was thrown", ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
