package br.com.luan.barcella.jesus.api.dto.response;

import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.BooleanUtils.negate;

import java.io.Serializable;
import java.util.Map;

import org.springframework.http.converter.HttpMessageNotReadableException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.luan.barcella.jesus.api.domain.ErrorType;
import br.com.luan.barcella.jesus.api.domain.Message;
import br.com.luan.barcella.jesus.api.exception.AbstractErrorException;
import br.com.luan.barcella.jesus.api.exception.ClientErrorException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ErrorResponse implements Serializable {

    private static final long serialVersionUID = -8659539917093650919L;

    private final ErrorType errorType;
    private final String message;
    private final Map<String, String> details;

    public static ErrorResponse build(Exception ex) {
        if (ex instanceof AbstractErrorException) {
            return build((AbstractErrorException) ex);
        }

        if (ex instanceof HttpMessageNotReadableException) {
            return build((HttpMessageNotReadableException) ex);
        }

        return buildDefault();
    }

    public static ErrorResponse build(AbstractErrorException ex) {
        return ErrorResponse.builder().errorType(ex.getErrorType()).message(ex.getMessage())
            .details(negate(ex.getDetails().isEmpty()) ? ex.getDetails() : null).build();
    }

    public static ErrorResponse build(final HttpMessageNotReadableException ex) {
        return ErrorResponse.builder()
            .errorType(ErrorType.VALIDATION)
            .message(getMessageFromCause(ex.getCause()))
            .details(getDetailsFromCause(ex.getCause()))
            .build();
    }

    public static ErrorResponse buildDefault() {
        return ErrorResponse.builder().errorType(ErrorType.INTERNAL_ERROR).message(
            Message.FALHA_INESPERADA.name().replace("_", " ")).build();
    }


    private static String getMessageFromCause(final Throwable throwable) {
        return ofNullable(throwable)
            .map(Throwable::getCause)
            .filter(ClientErrorException.class::isInstance)
            .map(Throwable::getMessage)
            .orElse("Requisição inválida.");
    }

    private static Map<String, String> getDetailsFromCause(final Throwable throwable) {
        return ofNullable(throwable)
            .map(Throwable::getCause)
            .filter(ClientErrorException.class::isInstance)
            .map(ClientErrorException.class::cast)
            .map(ClientErrorException::getDetails)
            .orElse(emptyMap());
    }

}
