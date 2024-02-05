package br.com.luan.barcella.jesus.api.exception;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import br.com.luan.barcella.jesus.api.domain.ErrorType;
import lombok.Getter;

@Getter
public abstract class AbstractErrorException extends RuntimeException {

    private static final long serialVersionUID = -8004100005407012963L;

    private final Map<String, String> details;

    public AbstractErrorException(String msg) {
        super(msg);
        details = new ConcurrentHashMap<>();
    }

    public AbstractErrorException(String msg, Throwable th) {
        super(msg, th);
        details = new ConcurrentHashMap<>();
    }

    public abstract ErrorType getErrorType();

}
