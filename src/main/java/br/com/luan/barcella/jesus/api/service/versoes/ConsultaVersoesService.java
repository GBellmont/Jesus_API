package br.com.luan.barcella.jesus.api.service.versoes;

import java.io.Serial;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersoesBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersoesResponse;
import br.com.luan.barcella.jesus.api.mapper.versoes.ConsultaVersoesResponseMapper;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaVersoesService {

    private static final ConsultaVersoesResponseMapper CONSULTA_VERSOES_RESPONSE_MAPPER = new ConsultaVersoesResponseMapper();

    private final BibliaDigitalRestIntegration bibliaDigitalRestIntegration;

    public ConsultaVersoesResponse consultarVersoes() {
        log.info("Iniciando o processo de consulta de versões da bíblia digital.");

        final List<ConsultaVersoesBibliaDigitalResponse> versoes = bibliaDigitalRestIntegration.consultarVersoes();

        return CONSULTA_VERSOES_RESPONSE_MAPPER.apply(versoes);
    }

}
