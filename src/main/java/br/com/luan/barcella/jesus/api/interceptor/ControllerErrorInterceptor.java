package br.com.luan.barcella.jesus.api.interceptor;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.luan.barcella.jesus.api.exception.ClientErrorException;
import br.com.luan.barcella.jesus.api.dto.response.ErrorResponse;
import br.com.luan.barcella.jesus.api.exception.ServerErrorException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerErrorInterceptor {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ClientErrorException clientErrorException) {
        ErrorResponse error = ErrorResponse.builder().errorType(clientErrorException.getErrorType())
                        .message(clientErrorException.getMessage()).details(clientErrorException.getDetails()).build();

        HttpStatus httpStatus = error.getErrorType().getHttpStatus();

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(final HttpMessageNotReadableException exception) {
        final ErrorResponse error = ErrorResponse.build(exception);

        log.error("Não foi possível converter a requisição", exception);

        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ServerErrorException serverErrorException) {
        ErrorResponse error = ErrorResponse.builder().errorType(serverErrorException.getErrorType())
                        .message(serverErrorException.getMessage()).details(serverErrorException.getDetails()).build();

        log.error("Erro Interno {}", serverErrorException.getMessage());

        return new ResponseEntity<>(error, INTERNAL_SERVER_ERROR);
    }

}
