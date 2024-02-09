package br.com.luan.barcella.jesus.api.service.rest.integration;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import br.com.luan.barcella.jesus.api.domain.CacheName;
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

    private static final String PATH_CONSULTA_LIVRO = "/books/%s";
    private static final String PATH_CONSULTAR_LIVROS = "/books";
    private static final String PATH_CONSULTAR_VERSOES = "/versions";

    @Value("${integration.biblia-digital.url}")
    private String urlBibliaDigital;

    @Cacheable(CacheName.CONSULTA_LIVROS)
    public List<ConsultaLivrosBibliaDigitalResponse> consultarLivros() {
        final String url = urlBibliaDigital + PATH_CONSULTAR_LIVROS;

        log.info("Realizando chamada para a API da bíblia digital, na url: {}", url);

        return stream(ofNullable(this.get(url, ConsultaLivrosBibliaDigitalResponse[].class))
            .orElseGet(() -> new ConsultaLivrosBibliaDigitalResponse[0]))
            .toList();
    }

    @Cacheable(CacheName.CONSULTA_LIVRO)
    public ConsultaLivroBibliaDigitalResponse consultarLivro(final String abreviacao) {
        final String url = urlBibliaDigital + format(PATH_CONSULTA_LIVRO, abreviacao);

        log.info("Realizando chamada para a API da bíblia digital, na url: {}", url);

        return this.get(url, ConsultaLivroBibliaDigitalResponse.class);
    }

    public List<ConsultaVersoesBibliaDigitalResponse> consultarVersoes() {
        final String url = urlBibliaDigital + PATH_CONSULTAR_VERSOES;

        log.info("Realizando chamada para a API da bíblia digital, na url: {}", url);

        return stream(ofNullable(this.get(url, ConsultaVersoesBibliaDigitalResponse[].class))
            .orElseGet(() -> new ConsultaVersoesBibliaDigitalResponse[0]))
            .toList();
    }
}
