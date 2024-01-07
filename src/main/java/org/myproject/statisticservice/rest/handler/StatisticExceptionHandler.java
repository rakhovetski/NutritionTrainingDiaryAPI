package org.myproject.statisticservice.rest.handler;

import lombok.extern.slf4j.Slf4j;
import org.myproject.statisticservice.dto.ErrorResponseDto;
import org.myproject.statisticservice.exceptions.IncorrectBodyStatDataException;
import org.myproject.statisticservice.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class StatisticExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponseDto handle(NotFoundException ex) {
        log.error("Object by id doesn't exists: " + ex.getMessage());
        return new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }


    @ExceptionHandler(IncorrectBodyStatDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponseDto handle(IncorrectBodyStatDataException ex) {
        return new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
}
