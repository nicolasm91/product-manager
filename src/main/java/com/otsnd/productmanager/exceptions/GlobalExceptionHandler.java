package com.otsnd.productmanager.exceptions;

import com.otsnd.productmanager.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception) {
        LOGGER.error(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        Constants.ERROR_MESSAGE, exception.getMessage(),
                        Constants.TIMESTAMP, LocalDateTime.now()));
    }

    @ExceptionHandler(ProductMissingException.class)
    public ResponseEntity<?> handleProductMissingException(ProductMissingException exception) {
        LOGGER.error(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        Constants.ERROR_MESSAGE, exception.getMessage(),
                        Constants.TIMESTAMP, LocalDateTime.now()));
    }

    @ExceptionHandler(RepeatedProductInRequestException.class)
    public ResponseEntity<?> handleRepeatedProductInRequestException(RepeatedProductInRequestException exception) {
        LOGGER.error(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        Constants.ERROR_MESSAGE, exception.getMessage(),
                        Constants.TIMESTAMP, LocalDateTime.now()));
    }

    @ExceptionHandler(ExceededStockException.class)
    public ResponseEntity<?> handleExceededStockException(ExceededStockException exception) {
        LOGGER.error(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        Constants.ERROR_MESSAGE, exception.getMessage(),
                        Constants.TIMESTAMP, LocalDateTime.now(),
                        Constants.EXCEED_STOCK, exception.getExceedStock()));
    }
}
