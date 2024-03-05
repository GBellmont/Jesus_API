package br.com.luan.barcella.jesus.api.validator;

import static br.com.luan.barcella.jesus.api.domain.ErrorType.VALIDATION;
import static br.com.luan.barcella.jesus.api.domain.Message.ABREVIACAO_INVALIDA;
import static br.com.luan.barcella.jesus.api.domain.Message.CAPITULO_INVALIDO;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

import java.util.Objects;

import org.apache.logging.log4j.util.TriConsumer;
import org.springframework.stereotype.Component;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivrosBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.exception.ClientErrorException;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;
import br.com.luan.barcella.jesus.api.service.support.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsultaCapituloValidator implements TriConsumer<String, String, Integer> {

    private final BibliaDigitalRestIntegration bibliaDigitalRestIntegration;
    private final VersaoValidator versaoValidator;
    private final MessageService messageService;

    @Override
    public void accept(final String versao, final String abreviacao, final Integer capitulo) {

        versaoValidator.accept(versao);

        final ConsultaLivrosBibliaDigitalResponse livro = getLivroPorAbreviacao(abreviacao);

        if (isBlank(abreviacao) || isNull(livro)) {
            log.warn("Abreviação inválida ou não informada.");
            throw new ClientErrorException(VALIDATION, messageService.get(ABREVIACAO_INVALIDA));
        }

        if (isNull(capitulo) || verificaNaoExistenciaCapitulo(livro, capitulo)) {
            log.warn("Capítulo inválido ou não informado.");
            throw new ClientErrorException(VALIDATION, messageService.get(CAPITULO_INVALIDO));
        }
    }

    private ConsultaLivrosBibliaDigitalResponse getLivroPorAbreviacao(final String abreviacao) {
        return bibliaDigitalRestIntegration.consultarLivros()
            .stream()
            .filter(Objects::nonNull)
            .filter(livroNaoNull -> nonNull(livroNaoNull.getAbreviacao()))
            .filter((livroComAbreviacao) -> Objects.equals(abreviacao, livroComAbreviacao.getAbreviacao().getPortugues()))
            .findFirst()
            .orElse(null);
    }

    private boolean verificaNaoExistenciaCapitulo(final ConsultaLivrosBibliaDigitalResponse livro, final Integer capitulo) {
        return capitulo > livro.getNumeroCapitulos();
    }
}
