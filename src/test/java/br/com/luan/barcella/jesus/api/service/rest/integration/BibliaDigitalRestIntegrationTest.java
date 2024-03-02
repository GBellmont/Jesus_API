package br.com.luan.barcella.jesus.api.service.rest.integration;

import static br.com.luan.barcella.jesus.api.fixture.Fixture.make;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivroBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivrosBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersoesBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.service.support.MessageService;
import lombok.SneakyThrows;

@RunWith(MockitoJUnitRunner.class)
public class BibliaDigitalRestIntegrationTest {

    private static final String BEARER_TOKEN = "Bearer ";

    private static final String PATH_CONSULTAR_LIVRO = "/books/%s";
    private static final String PATH_CONSULTAR_LIVROS = "/books";
    private static final String PATH_CONSULTAR_VERSOES = "/versions";

    @InjectMocks
    private BibliaDigitalRestIntegration bibliaDigitalRestIntegration;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private MessageService messageService;

    @Captor
    private ArgumentCaptor<HttpEntity<?>> httpEntityCaptor;

    private String url;

    private String token;

    private HttpHeaders headers;

    private Object body;

    @Before
    public void init() {

        url = randomAlphabetic(20);
        token = randomAlphabetic(10);
        headers = make(new HttpHeaders());
        body = make(new Object());

        setField(bibliaDigitalRestIntegration, "restTemplate", restTemplate);
        setField(bibliaDigitalRestIntegration, "messageService", messageService);
        setField(bibliaDigitalRestIntegration, "urlBibliaDigital", url);
        setField(bibliaDigitalRestIntegration, "authorizationBibliaDigital", token);
    }

    @Test
    @SneakyThrows
    public void annotationCacheableConsultarLivros() {
        Assert.assertTrue(bibliaDigitalRestIntegration.getClass()
            .getDeclaredMethod("consultarLivros")
            .isAnnotationPresent(Cacheable.class));
    }

    @Test
    public void consultarLivrosSucesso() {
        final String urlExpected = url + PATH_CONSULTAR_LIVROS;
        final HttpMethod httpMethodExpected = GET;
        final Class<ConsultaLivrosBibliaDigitalResponse[]> responseClass = ConsultaLivrosBibliaDigitalResponse[].class;

        final ResponseEntity<ConsultaLivrosBibliaDigitalResponse[]> entityResponse = ok(new ConsultaLivrosBibliaDigitalResponse[10]);

        when(restTemplate.exchange(eq(urlExpected), eq(httpMethodExpected), any(HttpEntity.class), eq(responseClass)))
            .thenReturn(entityResponse);

        final List<ConsultaLivrosBibliaDigitalResponse> response = bibliaDigitalRestIntegration.consultarLivros();

        verify(restTemplate).exchange(eq(urlExpected), eq(httpMethodExpected), httpEntityCaptor.capture(), eq(responseClass));

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertNull(httpEntityCaptured.getBody());
        assertHeaders(httpEntityCaptured.getHeaders());

        assertNotNull(response);
    }

    @Test
    @SneakyThrows
    public void annotationCacheableConsultarLivro() {
        Assert.assertTrue(bibliaDigitalRestIntegration.getClass()
            .getDeclaredMethod("consultarLivro", String.class)
            .isAnnotationPresent(Cacheable.class));
    }

    @Test
    public void consultarLivroSucesso() {
        final String abreviacao = randomAlphabetic(5);
        final String urlExpected = url + String.format(PATH_CONSULTAR_LIVRO, abreviacao);
        final HttpMethod httpMethodExpected = GET;
        final Class<ConsultaLivroBibliaDigitalResponse> responseClass = ConsultaLivroBibliaDigitalResponse.class;

        final ResponseEntity<ConsultaLivroBibliaDigitalResponse> entityResponse = ok(make(new ConsultaLivroBibliaDigitalResponse()));

        when(restTemplate.exchange(eq(urlExpected), eq(httpMethodExpected), any(HttpEntity.class), eq(responseClass)))
            .thenReturn(entityResponse);

        final ConsultaLivroBibliaDigitalResponse response = bibliaDigitalRestIntegration.consultarLivro(abreviacao);

        verify(restTemplate).exchange(eq(urlExpected), eq(httpMethodExpected), httpEntityCaptor.capture(), eq(responseClass));

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertNull(httpEntityCaptured.getBody());
        assertHeaders(httpEntityCaptured.getHeaders());

        assertNotNull(response);
    }

    @Test
    public void consultarVersoesSucesso() {
        final String urlExpected = url + PATH_CONSULTAR_VERSOES;
        final HttpMethod httpMethodExpected = GET;
        final Class<ConsultaVersoesBibliaDigitalResponse[]> responseClass = ConsultaVersoesBibliaDigitalResponse[].class;

        final ResponseEntity<ConsultaVersoesBibliaDigitalResponse[]> entityResponse = ok(new ConsultaVersoesBibliaDigitalResponse[10]);

        when(restTemplate.exchange(eq(urlExpected), eq(httpMethodExpected), any(HttpEntity.class), eq(responseClass)))
            .thenReturn(entityResponse);

        final List<ConsultaVersoesBibliaDigitalResponse> response = bibliaDigitalRestIntegration.consultarVersoes();

        verify(restTemplate).exchange(eq(urlExpected), eq(httpMethodExpected), httpEntityCaptor.capture(), eq(responseClass));

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertNull(httpEntityCaptured.getBody());
        assertHeaders(httpEntityCaptured.getHeaders());

        assertNotNull(response);
    }

    private void assertHeaders(final HttpHeaders headers) {
        assertNotNull(headers);
        assertFalse(headers.isEmpty());

        assertTrue(headers.containsKey(ACCEPT));
        assertTrue(headers.containsKey(CONTENT_TYPE));
        assertTrue(headers.containsKey(AUTHORIZATION));

        assertEquals(APPLICATION_JSON.getMimeType(), headers.getFirst(ACCEPT));
        assertEquals(APPLICATION_JSON.getMimeType(), headers.getFirst(CONTENT_TYPE));
        assertEquals((BEARER_TOKEN + token), headers.getFirst(AUTHORIZATION));
    }

}
