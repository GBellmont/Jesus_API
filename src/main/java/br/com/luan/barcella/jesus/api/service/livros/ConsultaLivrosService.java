package br.com.luan.barcella.jesus.api.service.livros;

import static br.com.luan.barcella.jesus.api.service.utils.PaginacaoService.paginarComObjetoResponse;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.luan.barcella.jesus.api.dto.response.ConsultaLivrosResponse;
import br.com.luan.barcella.jesus.api.dto.response.PaginacaoResponse;
import br.com.luan.barcella.jesus.api.mapper.livros.ConsultaLivrosResponseMapper;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;
import br.com.luan.barcella.jesus.api.validator.PaginacaoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaLivrosService {

    private static final ConsultaLivrosResponseMapper CONSULTA_LIVROS_RESPONSE_MAPPER = new ConsultaLivrosResponseMapper();

    private final BibliaDigitalRestIntegration bibliaDigitalRestIntegration;
    private final PaginacaoValidator paginacaoValidator;

    public PaginacaoResponse<ConsultaLivrosResponse> consultarLivros(final Integer index, final Integer numeroItens) {
        log.info("Iniciando o proceso de consulta dos livros da bíblia.");
        paginacaoValidator.accept(index, numeroItens);

        final List<ConsultaLivrosResponse> livros = CONSULTA_LIVROS_RESPONSE_MAPPER.apply(
            bibliaDigitalRestIntegration.consultarLivros());

        return paginarComObjetoResponse(index, numeroItens, livros);
    }
}
