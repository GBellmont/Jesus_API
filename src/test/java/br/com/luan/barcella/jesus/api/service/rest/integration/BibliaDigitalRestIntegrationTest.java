package br.com.luan.barcella.jesus.api.service.rest.integration;

import static br.com.luan.barcella.jesus.api.fixture.Fixture.make;
import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
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
import static org.springframework.http.HttpMethod.POST;
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

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.request.ConsultaVersosPorPalavraBibliaDigitalRequest;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaCapituloBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivroBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivrosBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersoAleatorioBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersoesBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersosPorPalavraBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.service.support.MessageService;
import lombok.SneakyThrows;

@RunWith(MockitoJUnitRunner.class)
public class BibliaDigitalRestIntegrationTest {

    private static final String BEARER_TOKEN = "Bearer ";

    private static final String PATH_CONSULTA_LIVRO = "/books/%s";
    private static final String PATH_CONSULTA_CAPITULO = "/verses/%s/%s/%s";
    private static final String PATH_CONSULTA_LIVROS = "/books";
    private static final String PATH_CONSULTA_VERSO_ALEATORIO = "/verses/%s/random";
    private static final String PATH_CONSULTA_VERSOS_POR_PALAVRA = "/verses/search";
    private static final String PATH_CONSULTA_VERSOES = "/versions";

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
        final String urlExpected = url + PATH_CONSULTA_LIVROS;
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
        final String urlExpected = url + format(PATH_CONSULTA_LIVRO, abreviacao);
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
    @SneakyThrows
    public void annotationCacheableConsultarVersoes() {
        Assert.assertTrue(bibliaDigitalRestIntegration.getClass()
            .getDeclaredMethod("consultarVersoes")
            .isAnnotationPresent(Cacheable.class));
    }

    @Test
    public void consultarVersoesSucesso() {
        final String urlExpected = url + PATH_CONSULTA_VERSOES;
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

    @Test
    @SneakyThrows
    public void annotationCacheableConsultarCapitulo() {
        Assert.assertTrue(bibliaDigitalRestIntegration.getClass()
            .getDeclaredMethod("consultarCapitulo", String.class, String.class, Integer.class)
            .isAnnotationPresent(Cacheable.class));
    }

    @Test
    public void consultarCapituloSucesso() {
        final String versao = randomAlphabetic(3);
        final String abreviacao = randomAlphabetic(3);
        final Integer capitulo = nextInt();

        final String urlExpected = url + format(PATH_CONSULTA_CAPITULO, versao, abreviacao, capitulo);
        final HttpMethod httpMethodExpected = GET;
        final Class<ConsultaCapituloBibliaDigitalResponse> responseClass = ConsultaCapituloBibliaDigitalResponse.class;

        final ResponseEntity<ConsultaCapituloBibliaDigitalResponse> entityResponse = ok(new ConsultaCapituloBibliaDigitalResponse());

        when(restTemplate.exchange(eq(urlExpected), eq(httpMethodExpected), any(HttpEntity.class), eq(responseClass)))
            .thenReturn(entityResponse);

        final ConsultaCapituloBibliaDigitalResponse response = bibliaDigitalRestIntegration.consultarCapitulo(versao, abreviacao, capitulo);

        verify(restTemplate).exchange(eq(urlExpected), eq(httpMethodExpected), httpEntityCaptor.capture(), eq(responseClass));

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertNull(httpEntityCaptured.getBody());
        assertHeaders(httpEntityCaptured.getHeaders());

        assertNotNull(response);
    }

    @Test
    public void consultarVersoAleatorioSucesso() {
        final String versao = randomAlphabetic(3);

        final String urlExpected = url + format(PATH_CONSULTA_VERSO_ALEATORIO, versao);
        final HttpMethod httpMethodExpected = GET;
        final Class<ConsultaVersoAleatorioBibliaDigitalResponse> responseClass = ConsultaVersoAleatorioBibliaDigitalResponse.class;

        final ResponseEntity<ConsultaVersoAleatorioBibliaDigitalResponse> entityResponse = ok(new ConsultaVersoAleatorioBibliaDigitalResponse());

        when(restTemplate.exchange(eq(urlExpected), eq(httpMethodExpected), any(HttpEntity.class), eq(responseClass)))
            .thenReturn(entityResponse);

        final ConsultaVersoAleatorioBibliaDigitalResponse response = bibliaDigitalRestIntegration.consultarVersoAleatorio(versao);

        verify(restTemplate).exchange(eq(urlExpected), eq(httpMethodExpected), httpEntityCaptor.capture(), eq(responseClass));

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertNull(httpEntityCaptured.getBody());
        assertHeaders(httpEntityCaptured.getHeaders());

        assertNotNull(response);
    }

    @Test
    @SneakyThrows
    public void annotationCacheableConsultarVersosPorPalavra() {
        Assert.assertTrue(bibliaDigitalRestIntegration.getClass()
            .getDeclaredMethod("consultarVersosPorPalavra", ConsultaVersosPorPalavraBibliaDigitalRequest.class)
            .isAnnotationPresent(Cacheable.class));
    }

    @Test
    public void consultarVersosPorPalavraSucesso() {
        final ConsultaVersosPorPalavraBibliaDigitalRequest request = make(new ConsultaVersosPorPalavraBibliaDigitalRequest());

        final String urlExpected = url + PATH_CONSULTA_VERSOS_POR_PALAVRA;
        final HttpMethod httpMethodExpected = POST;
        final Class<ConsultaVersosPorPalavraBibliaDigitalResponse> responseClass = ConsultaVersosPorPalavraBibliaDigitalResponse.class;

        final ResponseEntity<ConsultaVersosPorPalavraBibliaDigitalResponse> entityResponse = ok(new ConsultaVersosPorPalavraBibliaDigitalResponse());

        when(restTemplate.exchange(eq(urlExpected), eq(httpMethodExpected), any(HttpEntity.class), eq(responseClass)))
            .thenReturn(entityResponse);

        final ConsultaVersosPorPalavraBibliaDigitalResponse response = bibliaDigitalRestIntegration.consultarVersosPorPalavra(request);

        verify(restTemplate).exchange(eq(urlExpected), eq(httpMethodExpected), httpEntityCaptor.capture(), eq(responseClass));

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertNotNull(httpEntityCaptured.getBody());
        assertEquals(request, httpEntityCaptured.getBody());
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
