package br.com.luan.barcella.jesus.api.service.rest.integration;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import br.com.luan.barcella.jesus.api.domain.CacheName;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivrosBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.service.rest.AbstractRestApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BibliaDigitalRestIntegration extends AbstractRestApiService {

    private static final String PATH_CONSULTAR_LIVROS = "/books";

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
}
