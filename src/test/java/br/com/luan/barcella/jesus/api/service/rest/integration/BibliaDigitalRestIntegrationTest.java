package br.com.luan.barcella.jesus.api.service.rest.integration;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;
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

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivrosBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.fixture.Fixture;
import br.com.luan.barcella.jesus.api.service.support.MessageService;
import lombok.SneakyThrows;

@RunWith(MockitoJUnitRunner.class)
public class BibliaDigitalRestIntegrationTest {

    private static final String PATH_CONSULTAR_LIVROS = "/books";

    @InjectMocks
    private BibliaDigitalRestIntegration bibliaDigitalRestIntegration;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private MessageService messageService;

    @Captor
    private ArgumentCaptor<HttpEntity<?>> httpEntityCaptor;

    private String url;

    private HttpHeaders headers;

    private Object body;

    @Before
    public void init() {

        url = randomAlphabetic(20);
        headers = Fixture.make(new HttpHeaders());
        body = Fixture.make(new Object());

        setField(bibliaDigitalRestIntegration, "restTemplate", restTemplate);
        setField(bibliaDigitalRestIntegration, "messageService", messageService);
        setField(bibliaDigitalRestIntegration, "urlBibliaDigital", url);
    }

    @Test
    @SneakyThrows
    public void annotation_rabbitHandler() {
        Assert.assertTrue(bibliaDigitalRestIntegration.getClass()
            .getDeclaredMethod("consultarLivros")
            .isAnnotationPresent(Cacheable.class));
    }

    @Test
    public void consultarLivrosSucesso() {
        final String urlExpected = url + PATH_CONSULTAR_LIVROS;
        final HttpMethod httpMethodExpected = GET;
        final Class<ConsultaLivrosBibliaDigitalResponse[]> responseClass = ConsultaLivrosBibliaDigitalResponse[].class;

        final ResponseEntity<ConsultaLivrosBibliaDigitalResponse[]> entityResponse = ResponseEntity.ok(new ConsultaLivrosBibliaDigitalResponse[10]);

        when(restTemplate.exchange(eq(urlExpected), eq(httpMethodExpected), any(HttpEntity.class), eq(responseClass)))
            .thenReturn(entityResponse);

        final List<ConsultaLivrosBibliaDigitalResponse> response = bibliaDigitalRestIntegration.consultarLivros();

        verify(restTemplate).exchange(eq(urlExpected), eq(httpMethodExpected), httpEntityCaptor.capture(), eq(responseClass));

        final HttpEntity<?> httpEntityCaptured = httpEntityCaptor.getValue();
        assertNotNull(httpEntityCaptured);
        assertNull(httpEntityCaptured.getBody());
        assertTrue(httpEntityCaptured.getHeaders().isEmpty());

        assertNotNull(response);
    }

}
