package br.com.luan.barcella.jesus.api.service.versos;

import org.springframework.stereotype.Service;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.request.ConsultaVersosPorPalavraBibliaDigitalRequest;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersosPorPalavraBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.request.ConsultaVersosPorPalavraRequest;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersosPorPalavraResponse;
import br.com.luan.barcella.jesus.api.dto.response.PaginacaoResponse;
import br.com.luan.barcella.jesus.api.mapper.versos.ConsultaVersosPorPalavraResponseMapper;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;
import br.com.luan.barcella.jesus.api.service.utils.PaginacaoService;
import br.com.luan.barcella.jesus.api.validator.ConsultaVersosPorPalavraValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaVersosPorPalavraService {

    private static final ConsultaVersosPorPalavraResponseMapper CONSULTA_VERSOS_POR_PALAVRA_RESPONSE_MAPPER = new ConsultaVersosPorPalavraResponseMapper();

    private final ConsultaVersosPorPalavraValidator consultaVersosPorPalavraValidator;
    private final BibliaDigitalRestIntegration bibliaDigitalRestIntegration;

    public PaginacaoResponse<ConsultaVersosPorPalavraResponse> consultarVersosPorPalavra(final ConsultaVersosPorPalavraRequest request) {
        log.info("Iniciando o processo de consulta de versos por palavra para: {}", request);
        consultaVersosPorPalavraValidator.accept(request);

        final ConsultaVersosPorPalavraBibliaDigitalResponse response = bibliaDigitalRestIntegration
            .consultarVersosPorPalavra(buildRequest(request));

        return PaginacaoService.paginarComObjetoResponseMapper(
            request.getIndex(), request.getNumeroItens(), response.getVersos(), CONSULTA_VERSOS_POR_PALAVRA_RESPONSE_MAPPER);
    }

    private ConsultaVersosPorPalavraBibliaDigitalRequest buildRequest(final ConsultaVersosPorPalavraRequest request) {
        final ConsultaVersosPorPalavraBibliaDigitalRequest bibliaDigitalRequest = new ConsultaVersosPorPalavraBibliaDigitalRequest();
        bibliaDigitalRequest.setVersao(request.getVersao());
        bibliaDigitalRequest.setPalavra(request.getPalavra());

        return bibliaDigitalRequest;
    }
}
