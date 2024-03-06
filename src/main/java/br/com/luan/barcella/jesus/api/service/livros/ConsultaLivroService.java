package br.com.luan.barcella.jesus.api.service.livros;

import org.springframework.stereotype.Service;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivroBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaLivroResponse;
import br.com.luan.barcella.jesus.api.mapper.livros.ConsultaLivroResponseMapper;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;
import br.com.luan.barcella.jesus.api.validator.ConsultaLivroValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaLivroService {

    private static final ConsultaLivroResponseMapper CONSULTA_LIVRO_RESPONSE_MAPPER = new ConsultaLivroResponseMapper();

    private final ConsultaLivroValidator consultaLivroValidator;
    private final BibliaDigitalRestIntegration bibliaDigitalRestIntegration;

    public ConsultaLivroResponse consultarLivro(final String abreviacao) {
        log.info("Iniciando processo de consulta do livro de abreviação: {}", abreviacao);
        consultaLivroValidator.accept(abreviacao);

        final ConsultaLivroBibliaDigitalResponse livroResponse = bibliaDigitalRestIntegration.consultarLivro(abreviacao);

        return CONSULTA_LIVRO_RESPONSE_MAPPER.apply(livroResponse);
    }

}
