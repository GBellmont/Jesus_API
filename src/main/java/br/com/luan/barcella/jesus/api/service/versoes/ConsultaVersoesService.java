package br.com.luan.barcella.jesus.api.service.versoes;

import static br.com.luan.barcella.jesus.api.service.utils.PaginacaoService.paginarComObjetoResponseMapper;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersoesBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.VersaoResponse;
import br.com.luan.barcella.jesus.api.dto.response.PaginacaoResponse;
import br.com.luan.barcella.jesus.api.mapper.versoes.ConsultaVersoesResponseMapper;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;
import br.com.luan.barcella.jesus.api.validator.PaginacaoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaVersoesService {

    private static final ConsultaVersoesResponseMapper CONSULTA_VERSOES_RESPONSE_MAPPER = new ConsultaVersoesResponseMapper();

    private final BibliaDigitalRestIntegration bibliaDigitalRestIntegration;
    private final PaginacaoValidator paginacaoValidator;

    public PaginacaoResponse<VersaoResponse> consultarVersoes(final Integer index, final Integer numeroItens) {
        log.info("Iniciando o processo de consulta de versões da bíblia digital.");
        paginacaoValidator.accept(index, numeroItens);

        final List<ConsultaVersoesBibliaDigitalResponse> versoes = bibliaDigitalRestIntegration.consultarVersoes();

        return paginarComObjetoResponseMapper(
            index, numeroItens, versoes, CONSULTA_VERSOES_RESPONSE_MAPPER);
    }
}
