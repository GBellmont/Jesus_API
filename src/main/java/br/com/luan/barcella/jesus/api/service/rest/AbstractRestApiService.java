package br.com.luan.barcella.jesus.api.service.rest;

import static java.util.Arrays.stream;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import br.com.luan.barcella.jesus.api.domain.ErrorType;
import br.com.luan.barcella.jesus.api.domain.Message;
import br.com.luan.barcella.jesus.api.exception.ClientErrorException;
import br.com.luan.barcella.jesus.api.exception.ServerErrorException;
import br.com.luan.barcella.jesus.api.service.support.MessageService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractRestApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Getter
    @Autowired
    private MessageService messageService;

    /**
     * Método que retorna a exception que será jogada. Este pode ser utilizado para personalizar a exception conforme
     * regras de negocio impostas.
     *
     * @param ex Exception retornada pelo RestTemplate.
     * @return Exception para ser jogada.
     */
    protected RuntimeException buildError(final HttpStatusCodeException ex) {

        if (ex.getStatusCode().is5xxServerError()) {
            log.error("Server error Exception", ex);
            return new ServerErrorException(messageService.get(Message.SERVICO_INDISPONIVEL), ex);
        }

        final ErrorType errorType = stream(ErrorType.values())
            .filter(ert -> ert.getHttpStatus().equals(ex.getStatusCode()))
            .findFirst()
            .orElse(ErrorType.BUSINESS);

        final ClientErrorException error = new ClientErrorException(errorType,
            messageService.get(Message.SERVICO_INDISPONIVEL), ex);

        log.error("Server Error Exception: [message={}, details={}]", error.getMessage(), error.getDetails());

        return error;
    }

    public <T> T get(final String url, final Class<T> responseClass) {
        return performRest(url, new HttpHeaders(), null, responseClass, HttpMethod.GET);
    }

    public <T> T getWithHeaders(final String url, final HttpHeaders headers, final Class<T> responseClass) {
        return performRest(url, headers, null, responseClass, HttpMethod.GET);
    }

    public <T> T post(final String url, final HttpHeaders headers, final Class<T> responseClass) {
        return performRest(url, headers, null, responseClass, HttpMethod.POST);
    }

    public <T> T postWithBody(final String url, final Object body, final Class<T> responseClass) {
        return performRest(url, new HttpHeaders(), body, responseClass, HttpMethod.POST);
    }

    public <T> T postWithHeaders(final String url, final HttpHeaders headers, final Object body, final Class<T> responseClass) {
        return performRest(url, headers, body, responseClass, HttpMethod.POST);
    }


    public <T> T performRest(final String url, final HttpHeaders headers, final Object body,
        final Class<T> responseClass, final HttpMethod httpMethod) {
        try {
            final HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);

            log.info("REST Request. URL {} : {}", url, httpMethod);
            final ResponseEntity<T> response = restTemplate.exchange(url, httpMethod, httpEntity, responseClass);

            log.debug("REST Response. {}", ofNullable(response).map(HttpEntity::getBody).orElse(null));

            return ofNullable(response)
                .map(HttpEntity::getBody)
                .orElseThrow(() -> {
                    log.warn("Response nula na chamada.");
                    return new ServerErrorException(messageService.get(Message.SERVICO_INDISPONIVEL));
                });

        } catch (final HttpStatusCodeException ex) {
            log.error("Falha na API para url {}: [httpStatusCode:{}, responseBody:{}]", url,
                of(ex.getStatusCode()).map(HttpStatusCode::value).orElse(null), ex.getResponseBodyAsString());

            throw buildError(ex);
        }
    }
}

