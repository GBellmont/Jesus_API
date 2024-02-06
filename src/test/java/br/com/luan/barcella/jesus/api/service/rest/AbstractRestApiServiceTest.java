package br.com.luan.barcella.jesus.api.service.rest;

import static br.com.luan.barcella.jesus.api.domain.ErrorType.BUSINESS;
import static br.com.luan.barcella.jesus.api.domain.Message.SERVICO_INDISPONIVEL;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import br.com.luan.barcella.jesus.api.domain.ErrorType;
import br.com.luan.barcella.jesus.api.domain.Message;
import br.com.luan.barcella.jesus.api.exception.ClientErrorException;
import br.com.luan.barcella.jesus.api.exception.ServerErrorException;
import br.com.luan.barcella.jesus.api.fixture.Fixture;
import br.com.luan.barcella.jesus.api.service.support.MessageService;

@RunWith(MockitoJUnitRunner.class)
public class AbstractRestApiServiceTest {

    private AbstractRestApiService service;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private MessageService messageService;

    @Captor
    private ArgumentCaptor<HttpEntity<?>> httpEntityCaptor;

    private String url;

    private HttpHeaders headers;

    private Object body;

    private String message;

    @Before
    public void init() {
        service = new AbstractRestApiService() {
        };

        setField(service, "restTemplate", restTemplate);
        setField(service, "messageService", messageService);

        url = randomAlphabetic(20);
        headers = Fixture.make(new HttpHeaders());
        body = Fixture.make(new Object());
        message = randomAlphabetic(10);

        when(messageService.get(any(Message.class)))
            .thenReturn(message);
    }

    @Test
    public void performRestSucesso() {
        final HttpMethod httpMethod = POST;
        final Class<Object> responseClass = Object.class;
        final ResponseEntity<Object> entityResponse = ResponseEntity.ok(new Object());

        when(restTemplate.exchange(eq(url), eq(httpMethod), any(HttpEntity.class), eq(responseClass)))
            .thenReturn(entityResponse);

        final Object response = service.performRest(url, headers, body, responseClass, httpMethod);

        verify(restTemplate).exchange(eq(url), eq(httpMethod), httpEntityCaptor.capture(), eq(responseClass));

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertEquals(body, httpEntityCaptured.getBody());
        assertEquals(headers, httpEntityCaptured.getHeaders());

        assertNotNull(response);
    }

    @Test
    public void performRestResponseBodyNull() {
        final HttpMethod httpMethod = POST;
        final Class<Object> responseClass = Object.class;
        final ResponseEntity<Object> entityResponse = ResponseEntity.ok(null);

        when(restTemplate.exchange(eq(url), eq(httpMethod), any(HttpEntity.class), eq(responseClass)))
            .thenReturn(entityResponse);

        final ServerErrorException exception = Assertions.assertThrows(ServerErrorException.class, () -> {
            service.performRest(url, headers, body, responseClass, httpMethod);
        });

        verify(restTemplate).exchange(eq(url), eq(httpMethod), httpEntityCaptor.capture(), eq(responseClass));
        verify(messageService).get(SERVICO_INDISPONIVEL);

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertEquals(body, httpEntityCaptured.getBody());
        assertEquals(headers, httpEntityCaptured.getHeaders());

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void performRestBuildError5xx() {
        final HttpMethod httpMethod = POST;
        final Class<Object> responseClass = Object.class;
        final HttpStatusCodeException httpStatusCodeException = new HttpClientErrorException(INTERNAL_SERVER_ERROR);

        when(restTemplate.exchange(eq(url), eq(httpMethod), any(HttpEntity.class), eq(responseClass)))
            .thenThrow(httpStatusCodeException);

        final ServerErrorException exception = Assertions.assertThrows(ServerErrorException.class, () -> {
            service.performRest(url, headers, body, responseClass, httpMethod);
        });

        verify(restTemplate).exchange(eq(url), eq(httpMethod), httpEntityCaptor.capture(), eq(responseClass));
        verify(messageService).get(SERVICO_INDISPONIVEL);

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertEquals(body, httpEntityCaptured.getBody());
        assertEquals(headers, httpEntityCaptured.getHeaders());

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void performRestBuildError4xxComErrorTypeMapeado() {
        final HttpMethod httpMethod = POST;
        final Class<Object> responseClass = Object.class;
        final HttpStatusCodeException httpStatusCodeException = new HttpClientErrorException(HttpStatus.NOT_FOUND);

        when(restTemplate.exchange(eq(url), eq(httpMethod), any(HttpEntity.class), eq(responseClass)))
            .thenThrow(httpStatusCodeException);

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            service.performRest(url, headers, body, responseClass, httpMethod);
        });

        verify(restTemplate).exchange(eq(url), eq(httpMethod), httpEntityCaptor.capture(), eq(responseClass));
        verify(messageService).get(SERVICO_INDISPONIVEL);

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertEquals(body, httpEntityCaptured.getBody());
        assertEquals(headers, httpEntityCaptured.getHeaders());

        assertNotNull(exception);
        assertEquals(ErrorType.NOT_FOUND, exception.getErrorType());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void performRestBuildError4xxComErrorTypeNaoMapeado() {
        final HttpMethod httpMethod = POST;
        final Class<Object> responseClass = Object.class;
        final HttpStatusCodeException httpStatusCodeException = new HttpClientErrorException(TOO_MANY_REQUESTS);

        when(restTemplate.exchange(eq(url), eq(httpMethod), any(HttpEntity.class), eq(responseClass)))
            .thenThrow(httpStatusCodeException);

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            service.performRest(url, headers, body, responseClass, httpMethod);
        });

        verify(restTemplate).exchange(eq(url), eq(httpMethod), httpEntityCaptor.capture(), eq(responseClass));
        verify(messageService).get(SERVICO_INDISPONIVEL);

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertEquals(body, httpEntityCaptured.getBody());
        assertEquals(headers, httpEntityCaptured.getHeaders());

        assertNotNull(exception);
        assertEquals(BUSINESS, exception.getErrorType());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void performGetSucesso() {
        final HttpMethod httpMethod = GET;
        final Class<Object> responseClass = Object.class;
        final ResponseEntity<Object> entityResponse = ResponseEntity.ok(new Object());

        when(restTemplate.exchange(eq(url), eq(httpMethod), any(HttpEntity.class), eq(responseClass)))
            .thenReturn(entityResponse);

        final Object response = service.get(url, responseClass);

        verify(restTemplate).exchange(eq(url), eq(httpMethod), httpEntityCaptor.capture(), eq(responseClass));

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertNull(httpEntityCaptured.getBody());
        assertTrue(httpEntityCaptured.getHeaders().isEmpty());

        assertNotNull(response);
    }

    @Test
    public void performGetWithHeadersSucesso() {
        final HttpMethod httpMethod = GET;
        final Class<Object> responseClass = Object.class;
        final ResponseEntity<Object> entityResponse = ResponseEntity.ok(new Object());

        when(restTemplate.exchange(eq(url), eq(httpMethod), any(HttpEntity.class), eq(responseClass)))
            .thenReturn(entityResponse);

        final Object response = service.getWithHeaders(url, headers, responseClass);

        verify(restTemplate).exchange(eq(url), eq(httpMethod), httpEntityCaptor.capture(), eq(responseClass));

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertNull(httpEntityCaptured.getBody());
        assertEquals(headers, httpEntityCaptured.getHeaders());

        assertNotNull(response);
    }

    @Test
    public void performPostSucesso() {
        final HttpMethod httpMethod = POST;
        final Class<Object> responseClass = Object.class;
        final ResponseEntity<Object> entityResponse = ResponseEntity.ok(new Object());

        when(restTemplate.exchange(eq(url), eq(httpMethod), any(HttpEntity.class), eq(responseClass)))
            .thenReturn(entityResponse);

        final Object response = service.post(url, headers, responseClass);

        verify(restTemplate).exchange(eq(url), eq(httpMethod), httpEntityCaptor.capture(), eq(responseClass));

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertNull(httpEntityCaptured.getBody());
        assertEquals(headers, httpEntityCaptured.getHeaders());

        assertNotNull(response);
    }

    @Test
    public void performPostWithBodySucesso() {
        final HttpMethod httpMethod = POST;
        final Class<Object> responseClass = Object.class;
        final ResponseEntity<Object> entityResponse = ResponseEntity.ok(new Object());

        when(restTemplate.exchange(eq(url), eq(httpMethod), any(HttpEntity.class), eq(responseClass)))
            .thenReturn(entityResponse);

        final Object response = service.postWithBody(url, body, responseClass);

        verify(restTemplate).exchange(eq(url), eq(httpMethod), httpEntityCaptor.capture(), eq(responseClass));

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertEquals(body, httpEntityCaptured.getBody());
        assertTrue(httpEntityCaptured.getHeaders().isEmpty());

        assertNotNull(response);
    }

    @Test
    public void performPostWithHeaderSucesso() {
        final HttpMethod httpMethod = POST;
        final Class<Object> responseClass = Object.class;
        final ResponseEntity<Object> entityResponse = ResponseEntity.ok(new Object());

        when(restTemplate.exchange(eq(url), eq(httpMethod), any(HttpEntity.class), eq(responseClass)))
            .thenReturn(entityResponse);

        final Object response = service.postWithHeaders(url, headers, body, responseClass);

        verify(restTemplate).exchange(eq(url), eq(httpMethod), httpEntityCaptor.capture(), eq(responseClass));

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertEquals(body, httpEntityCaptured.getBody());
        assertEquals(headers, httpEntityCaptured.getHeaders());

        assertNotNull(response);
    }

}
