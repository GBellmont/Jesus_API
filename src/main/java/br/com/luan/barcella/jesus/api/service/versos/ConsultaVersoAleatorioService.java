package br.com.luan.barcella.jesus.api.service.versos;

import org.springframework.stereotype.Service;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersoAleatorioBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersoAleatorioResponse;
import br.com.luan.barcella.jesus.api.mapper.versos.ConsultaVersoAleatorioResponseMapper;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;
import br.com.luan.barcella.jesus.api.validator.VersaoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaVersoAleatorioService {

    private static final ConsultaVersoAleatorioResponseMapper CONSULTA_VERSO_ALEATORIO_RESPONSE_MAPPER = new ConsultaVersoAleatorioResponseMapper();

    private final VersaoValidator versaoValidator;
    private final BibliaDigitalRestIntegration bibliaDigitalRestIntegration;

    public ConsultaVersoAleatorioResponse consultarVersoAleatorio(final String versao) {
        log.info("Iniciando o processo de consulta de um verso aleatório da versão: {}", versao);
        versaoValidator.accept(versao);

        final ConsultaVersoAleatorioBibliaDigitalResponse response = bibliaDigitalRestIntegration.consultarVersoAleatorio(versao);

        return CONSULTA_VERSO_ALEATORIO_RESPONSE_MAPPER.apply(response);
    }

}
