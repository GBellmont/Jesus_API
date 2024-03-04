package br.com.luan.barcella.jesus.api.service.rest.integration;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import br.com.luan.barcella.jesus.api.domain.CacheName;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaCapituloBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivroBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivrosBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersoesBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.service.rest.AbstractRestApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BibliaDigitalRestIntegration extends AbstractRestApiService {

    private static final String BEARER_TOKEN = "Bearer ";

    private static final String PATH_CONSULTA_LIVRO = "/books/%s";
    private static final String PATH_CONSULTAR_CAPITULO = "/verses/%s/%s/%s";
    private static final String PATH_CONSULTAR_LIVROS = "/books";
    private static final String PATH_CONSULTAR_VERSOES = "/versions";

    @Value("${integration.biblia-digital.url}")
    private String urlBibliaDigital;

    @Value("${integration.biblia-digital.authorization-bearer-token}")
    private String authorizationBibliaDigital;

    @Cacheable(CacheName.CONSULTA_LIVROS)
    public List<ConsultaLivrosBibliaDigitalResponse> consultarLivros() {
        final String url = urlBibliaDigital + PATH_CONSULTAR_LIVROS;

        log.info("Realizando chamada para a API da bíblia digital, na url: {}", url);

        return stream(ofNullable(this.getWithHeaders(url, this.getHttpHeaders(), ConsultaLivrosBibliaDigitalResponse[].class))
            .orElseGet(() -> new ConsultaLivrosBibliaDigitalResponse[0]))
            .toList();
    }

    @Cacheable(CacheName.CONSULTA_LIVRO)
    public ConsultaLivroBibliaDigitalResponse consultarLivro(final String abreviacao) {
        final String url = urlBibliaDigital + format(PATH_CONSULTA_LIVRO, abreviacao);

        log.info("Realizando chamada para a API da bíblia digital, na url: {}", url);

        return this.getWithHeaders(url, this.getHttpHeaders(), ConsultaLivroBibliaDigitalResponse.class);
    }

    @Cacheable(CacheName.CONSULTA_VERSOES)
    public List<ConsultaVersoesBibliaDigitalResponse> consultarVersoes() {
        final String url = urlBibliaDigital + PATH_CONSULTAR_VERSOES;

        log.info("Realizando chamada para a API da bíblia digital, na url: {}", url);

        return stream(ofNullable(this.getWithHeaders(url, this.getHttpHeaders(), ConsultaVersoesBibliaDigitalResponse[].class))
            .orElseGet(() -> new ConsultaVersoesBibliaDigitalResponse[0]))
            .toList();
    }

    @Cacheable(CacheName.CONSULTA_CAPITULO)
    public ConsultaCapituloBibliaDigitalResponse consultarCapitulo(final String versao, final String abreviacao, final Integer capitulo) {
        final String url = urlBibliaDigital + format(PATH_CONSULTAR_CAPITULO, versao, abreviacao, capitulo);

        log.info("Realizando chamada para a API da bíblia digital, na url: {}", url);

        return this.getWithHeaders(url, this.getHttpHeaders(), ConsultaCapituloBibliaDigitalResponse.class);
    }

    private HttpHeaders getHttpHeaders() {
        final HttpHeaders headers = new HttpHeaders();

        headers.set(ACCEPT, APPLICATION_JSON.getMimeType());
        headers.set(CONTENT_TYPE, APPLICATION_JSON.getMimeType());
        headers.set(AUTHORIZATION, (BEARER_TOKEN + authorizationBibliaDigital));

        return headers;
    }
}
