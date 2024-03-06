package br.com.luan.barcella.jesus.api.service.capitulos;

import org.springframework.stereotype.Service;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaCapituloBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaCapituloResponse;
import br.com.luan.barcella.jesus.api.mapper.capitulos.ConsultaCapituloResponseMapper;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;
import br.com.luan.barcella.jesus.api.validator.ConsultaCapituloValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaCapituloService {

    private static final ConsultaCapituloResponseMapper CONSULTA_CAPITULO_RESPONSE_MAPPER = new ConsultaCapituloResponseMapper();

    private final ConsultaCapituloValidator consultaCapituloValidator;
    private final BibliaDigitalRestIntegration bibliaDigitalRestIntegration;

    public ConsultaCapituloResponse consultarCapitulo(final String versao, final String abreviacao, final Integer capitulo) {
        log.info("Iniciando o processo de consulta do capítulo: {} do livro de abreviacao: {}[{}]", capitulo, abreviacao, versao);
        consultaCapituloValidator.accept(versao, abreviacao, capitulo);

        final ConsultaCapituloBibliaDigitalResponse capituloResponse = bibliaDigitalRestIntegration.consultarCapitulo(versao, abreviacao, capitulo);

        return CONSULTA_CAPITULO_RESPONSE_MAPPER.apply(capituloResponse);
    }

}
